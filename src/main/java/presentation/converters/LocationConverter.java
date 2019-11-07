package presentation.converters;

import data.dao.models.Location;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "locationConverter")
public class LocationConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Double x = Double.valueOf(s.split(";")[0]);
        Double y = Double.valueOf(s.split(";")[1]);
        return new Location(x, y);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o.toString();
    }
}
