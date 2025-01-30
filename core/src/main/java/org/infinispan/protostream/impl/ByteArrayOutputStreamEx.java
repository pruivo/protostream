package org.infinispan.protostream.impl;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import org.infinispan.protostream.LazyByteArrayOutputStream;

/**
 * Extends {@link java.io.ByteArrayOutputStream} and provides direct access to the internal buffer without making a copy.
 *
 * @author anistor@redhat.com
 * @since 4.0
 */
public final class ByteArrayOutputStreamEx extends ByteArrayOutputStream implements LazyByteArrayOutputStream {

   public ByteArrayOutputStreamEx() {
   }

   public ByteArrayOutputStreamEx(int size) {
      super(size);
   }

   public synchronized ByteBuffer getByteBuffer() {
      return ByteBuffer.wrap(buf, 0, count);
   }

   @Override
   public synchronized int getPosition() {
      return count;
   }

   @Override
   public synchronized void setPosition(int position) {
      this.count = position;
   }

   @Override
   public synchronized void ensureCapacity(int size) {
      if (buf == null) {
         buf = new byte[size];
      } else if (size > buf.length) {
         byte[] newbuf = new byte[size];
         System.arraycopy(buf, 0, newbuf, 0, count);
         buf = newbuf;
      }
   }

   @Override
   public synchronized byte[] getRawBuffer() {
      return buf;
   }
}
