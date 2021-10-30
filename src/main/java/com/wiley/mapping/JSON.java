

package com.wiley.mapping;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.gsonfire.GsonFireBuilder;
import okio.ByteString;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;

public class JSON {
    private Gson gson;
    private boolean isLenientOnJson = false;
    private ByteArrayAdapter byteArrayAdapter = new ByteArrayAdapter();

    public static GsonBuilder createGson() {
        GsonFireBuilder fireBuilder = new GsonFireBuilder();
        GsonBuilder builder = fireBuilder.createGsonBuilder();
        return builder;
    }

    public JSON() {
        gson = createGson()
            .registerTypeAdapter(byte[].class, byteArrayAdapter)
            .create();
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj Object
     * @return String representation of the JSON
     */
    public String serialize(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param <T>        Type
     * @param body       The JSON string
     * @param returnType The type to deserialize into
     * @return The deserialized Java object
     */
    public <T> T deserialize(String body, Type returnType) {
        try {
            if (isLenientOnJson) {
                JsonReader jsonReader = new JsonReader(new StringReader(body));
                jsonReader.setLenient(true);
                return gson.fromJson(jsonReader, returnType);
            } else {
                return gson.fromJson(body, returnType);
            }
        } catch (JsonParseException e) {
            if (returnType.equals(String.class))
                return (T) body;
            else throw (e);
        }
    }

    /**
     * Gson TypeAdapter for Byte Array type
     */
    public class ByteArrayAdapter extends TypeAdapter<byte[]> {

        @Override
        public void write(JsonWriter out, byte[] value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(ByteString.of(value).base64());
            }
        }

        @Override
        public byte[] read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String bytesAsBase64 = in.nextString();
                    ByteString byteString = ByteString.decodeBase64(bytesAsBase64);
                    return byteString.toByteArray();
            }
        }
    }

}
