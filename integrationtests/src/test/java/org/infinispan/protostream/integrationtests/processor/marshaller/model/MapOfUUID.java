package org.infinispan.protostream.integrationtests.processor.marshaller.model;

import org.infinispan.protostream.annotations.ProtoField;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MapOfUUID {

    @ProtoField(value = 1, mapImplementation = HashMap.class)
    public Map<String, UUID> data;

}
