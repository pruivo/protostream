package org.infinispan.protostream;

public interface LazyByteArrayOutputStream {
   int getPosition();
   void setPosition(int position);
   void ensureCapacity(int size);
   byte[] getRawBuffer();
}
