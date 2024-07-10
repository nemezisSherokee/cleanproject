package house.learning.cleanproject.orderprocessing.monitoring.interfaces;

import org.apache.commons.lang.NotImplementedException;

public interface ICreatedCounter {
    default   void incrementCounter() {
        throw new NotImplementedException("ICreatedCounter.incrementCounter muss be implemented");
    }

}
