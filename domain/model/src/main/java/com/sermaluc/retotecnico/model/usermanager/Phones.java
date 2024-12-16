package com.sermaluc.retotecnico.model.usermanager;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class Phones {
    protected String number;
    protected String citycode;
    protected String countrycode;
}
