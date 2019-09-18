package mx.com.kubo.techTalk.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperConverter {

  @Autowired
  ModelMapper modelMapper;

  public Object mapTo(Object sourceObject, Class<?> targetClass) {
    return modelMapper.map(sourceObject, targetClass);
  }

}
