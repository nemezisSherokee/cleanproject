package house.learning.cleanproject.orderprocessing.monitoring.interfaces;

import org.apache.commons.lang.NotImplementedException;

public interface IActiveRequestsGauge {

    default int increment() {
        throw new NotImplementedException("IActiveOrderRequestsGauge.increment muss be implemented");
    }

    default int decrement() {
        throw new NotImplementedException("IActiveOrderRequestsGauge.decrement muss be implemented");
    }
}
