package pb138.service.mapper;

import java.util.Collection;
import java.util.List;


public interface Automapper {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object object, Class<T> mapToClass);
}
