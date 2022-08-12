package academy.jairo.springboot.camel.demo.util;

import academy.jairo.springboot.camel.demo.model.Constants;
import academy.jairo.springboot.camel.demo.model.dto.ContactDTO;
import academy.jairo.springboot.camel.demo.model.entity.Contact;
import academy.jairo.springboot.camel.demo.validation.BatchField;
import org.springframework.util.ReflectionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
public class FileLayoutGenerator {
    public static String[] getHeadersFromDTOWithoutDefault() throws ParseException {

        List<String> headers = new ArrayList<>();
        ContactDTO contactDTO = new ContactDTO();
        ReflectionUtils.doWithFields(contactDTO.getClass(), field -> {
            if (field.isAnnotationPresent(BatchField.class)) {
                headers.add(field.getName());
            }
        });
        String[] headersArray = new String[headers.size()];
        return headers.toArray(headersArray);
    }

    private static String getTrailers(int totalValues) {
        return "9".concat(Constants.SEPARATOR).concat(String.valueOf(totalValues));
    }

    private static String getLayoutVersion() {
        return "V1.0";
    }

    private static List<String> getValues(List<Contact> listContact) throws ParseException {
        List<String> valuesList = new ArrayList<String>();

        for (Contact contact : listContact) {
            StringBuilder values = new StringBuilder();

            ReflectionUtils.doWithFields(ContactDTO.class, field -> {
                if (field.isAnnotationPresent(BatchField.class)) {
                    field.setAccessible(true);
                    values.append(String.valueOf(field.get(contact)).concat(Constants.SEPARATOR));
                }
            });
            valuesList.add(values.toString().substring(0, values.length() - Constants.SEPARATOR.length()));

        }

        return valuesList;
    }

    public static String formatDate(Date date, String pattern) throws ParseException {

        DateFormat formatter1 = new SimpleDateFormat(pattern);
        return Objects.isNull(date) ? null : formatter1.format(date);
    }

}
