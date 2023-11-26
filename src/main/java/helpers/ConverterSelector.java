package helpers;

import java.util.List;

public class ConverterSelector {
    List<Converter> converters;

    public ConverterSelector(String baseCurr, String targetCurr) {
        BasicConverter basicConverter = new BasicConverter(baseCurr, targetCurr);
        ReverseRatesConverter reverseRatesConverter = new ReverseRatesConverter(baseCurr, targetCurr);
        NoRatesConverter noRatesConverter = new NoRatesConverter(baseCurr, targetCurr);
        converters = List.of(basicConverter, reverseRatesConverter, noRatesConverter);
    }

    public Converter selectConverter() throws Exception {
        for (var converter : converters) {
            if (converter.isValid())
                return converter;
        }

        throw new Exception("Не найдено ни одной пары для конвертирования");
    }
}
