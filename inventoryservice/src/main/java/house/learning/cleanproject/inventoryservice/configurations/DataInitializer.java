package house.learning.cleanproject.inventoryservice.configurations;

import house.learning.cleanproject.infrastructures.entities.*;
import house.learning.cleanproject.infrastructures.entities.helper.*;
import house.learning.cleanproject.inventoryservice.repositories.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

@Component
public class DataInitializer {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private FoodMenuRepository foodMenuRepository;
    @Autowired
    private DrinkMenuRepository drinkMenuRepository;
    @Autowired
    private ClientMenuRepository clientMenuRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ClientOrderRepository clientOrderRepository;
    @Autowired
    private RestaurantTableRepository tableRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private DayMenuOfferRepository dayMenuOfferRepository;


    @PostConstruct
    public void initDataFirst() {
        List<FoodMenu> savedFoodMenus = new ArrayList<>();
        List<DrinkMenu> savedDrinkMenus = new ArrayList<>();
        List<RestaurantTable> savedTables = new ArrayList<>();
        List<Client> savedClients = new ArrayList<>();

        // Create and save FoodMenus
        Flux<FoodMenu> foodMenus = Flux.range(1, 10)
                .map(i -> FoodMenuHelper.createRandomFoodMenu())
                .flatMap(foodMenuRepository::createFoodMenu)
                .doOnNext(savedFoodMenus::add);

        // Create and save DrinkMenus
        Flux<DrinkMenu> drinkMenus = Flux.range(1, 10)
                .map(i -> DrinkMenuHelper.createRandomDrinkMenu())
                .flatMap(drinkMenuRepository::createDrinkMenu)
                .doOnNext(savedDrinkMenus::add);

        Flux<RestaurantTable> tables = Flux.range(1, 10)
                .map(i -> TableHelper.createRandomTable())
                .flatMap(tableRepository::createRestaurantTable)
                .doOnNext(savedTables::add);

        tables.collectList().block().forEach(rt -> {
            savedClients.add(
                    clientRepository.createClient(ClientHelper.createRandomClient(rt)).block());
        });


        // Subscribe and wait for completion of both Flux streams
        Mono.when(foodMenus.collectList(), drinkMenus.collectList()).block();

        // Continue with ClientMenu creation if needed
        Flux<ClientMenu> clientMenus = Flux.range(1, 10)
                .map(i -> ClientMenuHelper.createRandomClientMenu(savedClients.get(RandomGenerator.getDefault().nextInt(0, savedClients.size())), savedFoodMenus, savedDrinkMenus));
        List<ClientMenu> savedClientMenus = clientMenus.flatMap(clientMenuRepository::createClientMenu).collectList().block();

        // Print the saved FoodMenus
        System.out.println("Saved FoodMenus:");
        savedFoodMenus.forEach(System.out::println);

        // Print the saved DrinkMenus
        System.out.println("Saved DrinkMenus:");
        savedDrinkMenus.forEach(System.out::println);

        var bills = savedClientMenus.stream().map(cm ->
                billRepository.createClientBill(cm,
                        cm.getDrinkMenus().stream().mapToDouble(f -> f.getPrice()).sum() +
                                cm.getFoodMenus().stream().mapToDouble(f -> f.getPrice()).sum()
                ).block()
        ).toList();

        System.out.println("Saved Bills:");
        bills.forEach(System.out::println);
        var clientOrders = bills.stream().map(bil ->
                clientOrderRepository.createClientOder( ClientOrderHelper.createRandomClientOrder(savedClients.get(RandomGenerator.getDefault().nextInt(0, savedClients.size())), bil),  LocalDateTime.now() ).block()
        ).toList();

        System.out.println("Saved clientOrders:");
        clientOrders.forEach(System.out::println);

        System.out.println("Saved Bills:");
        bills.forEach(System.out::println);

        var payements = bills.stream().map(bil ->
                paymentRepository.createClientBillPayment(PaymentHelper.createRandomPayment(bil), RandomDoubleGenerator.generate(0, bil.getTotalAmount())).block()
        ).toList();

        System.out.println("Saved Payment:");
        payements.forEach(System.out::println);

        var dayMenuOffer = dayMenuOfferRepository.createRestaurantTable(DayMenuOfferHelper.createRandomDayMenuOffer(savedFoodMenus.stream().collect(Collectors.toSet()),
                savedDrinkMenus.stream().collect(Collectors.toSet()))).block();

        System.out.println("Saved dayMenuOffer:");
        System.out.println(dayMenuOffer);

    }

}
