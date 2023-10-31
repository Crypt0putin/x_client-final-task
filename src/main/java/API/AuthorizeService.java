package API;

import java.io.IOException;

public interface AuthorizeService {

    String getToken() throws IOException;
}