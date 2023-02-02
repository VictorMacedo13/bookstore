package br.com.victor.bookstore.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;


import java.util.ArrayList;
import java.util.List;

public class ModelMapperV1{
    private static ModelMapper mapper = new ModelMapper();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseObjectList(List<O> origin, Class<D> destination) {
//        List<D> destList = mapper.map(origin, new TypeToken<List<D>>(){}.getType());
//        return destList;
        List<D> destinationObjects = new ArrayList<>();
        for (O o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }

}
