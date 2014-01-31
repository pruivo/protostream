package org.infinispan.protostream.test;

import com.google.protobuf.Descriptors;
import org.infinispan.protostream.ProtobufUtil;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.domain.marshallers.MarshallerRegistration;

import java.io.IOException;

/**
 * @author anistor@redhat.com
 * @since 1.0
 */
public abstract class AbstractProtoStreamTest {

   protected SerializationContext createContext() throws IOException, Descriptors.DescriptorValidationException {
      SerializationContext ctx = ProtobufUtil.newSerializationContext();
      MarshallerRegistration.registerMarshallers(ctx);
      return ctx;
   }
}
