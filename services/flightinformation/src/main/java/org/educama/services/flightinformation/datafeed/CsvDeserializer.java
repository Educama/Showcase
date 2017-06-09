package org.educama.services.flightinformation.datafeed;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CsvDeserializer<T> {
   
    public List<T> deserialize(InputStream in) throws IOException;
}
