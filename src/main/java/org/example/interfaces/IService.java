package org.example.interfaces;

import java.io.IOException;

public interface IService {
    public void initialize() throws IOException;
    public void destroy();
}
