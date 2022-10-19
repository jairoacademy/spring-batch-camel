package academy.jairo.springboot.batch.camel.model.mapper;

import academy.jairo.springboot.batch.camel.model.dto.ContactDTO;
import academy.jairo.springboot.batch.camel.model.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ContactMapper {

    public static final ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    public abstract Contact contactDtoToContact(ContactDTO contactDTO);

}
