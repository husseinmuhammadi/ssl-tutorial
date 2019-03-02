package com.javastudio.tutorial.ssl;

import java.io.OutputStream;

public interface OutputStreamCallback
{
    public void out(WebRequest r,Object metadata,OutputStream s);
}
