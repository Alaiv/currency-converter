package Services;

import helpers.Converter;
import helpers.ConverterSelector;

public class ExchangeService {
    public static double exchangeCurrencies(String from, String to, int amount) throws Exception {
        ConverterSelector converterSelector = new ConverterSelector(from, to);
        Converter converter = converterSelector.selectConverter();

        return converter.convert(amount);
    }
}
