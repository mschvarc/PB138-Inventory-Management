package pb138.service.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Dominik Gmiterko
 */
public class AutomapperImpl implements Automapper {

    private final Mapper dozer;

    public AutomapperImpl(DozerBeanMapper dozer) {
        this.dozer = dozer;

        List<String> mappingFiles = new ArrayList<>();
        mappingFiles.add("dozerJdk8Converters.xml");

        dozer.setMappingFiles(mappingFiles);
    }

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        if (objects == null) {
            return null;
        }

        List<T> mappedCollection = new ArrayList<>();

        for (Object object : objects) {
            mappedCollection.add(mapTo(object, mapToClass));
        }

        return mappedCollection;
    }

    @Override
    public <T> T mapTo(Object object, Class<T> mapToClass) {
        if (object == null) {
            return null;
        }

        return dozer.map(object, mapToClass);
    }
}
