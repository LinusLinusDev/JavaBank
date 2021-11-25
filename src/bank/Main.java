package bank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

public class Main {
    public static void main(String[] args){

    }
    static void serialisierenTest(){
        Payment x = new Payment("08.10.2021",-100,"Bargeldauszahlung am Freitag",0.04,0.03);
        IncomingTransfer y = new IncomingTransfer("13.10.2021",2.5,"TestTest","Ich","Du");
        OutgoingTransfer z = new OutgoingTransfer("24.04.2021", 50.0,"Geburtstagsgeschenk von Opa","Opa","Linus");

        GsonBuilder serializer = new GsonBuilder();

        serializer.registerTypeAdapter(Payment.class,new customSerializer());
        serializer.registerTypeAdapter(IncomingTransfer.class,new customSerializer());
        serializer.registerTypeAdapter(OutgoingTransfer.class,new customSerializer());

        Gson customSerializer = serializer.create();

        String customJson = customSerializer.toJson(z);
    }
    static void deserialisierenTest(){
        String customJsonX = "{\"CLASSNAME\":\"Payment\",\"INSTANCE\":{\"incomingInterest\":0.04,\"outgoingInterest\":0.03,\"date\":\"08.10.2021\",\"amount\":-100.0,\"description\":\"Bargeldauszahlung am Freitag\"}}";
        String customJsonY = "{\"CLASSNAME\":\"IncomingTransfer\",\"INSTANCE\":{\"sender\":\"Ich\",\"recipient\":\"Du\",\"date\":\"13.10.2021\",\"amount\":2.5,\"description\":\"TestTest\"}}";
        String customJsonZ = "{\"CLASSNAME\":\"OutgoingTransfer\",\"INSTANCE\":{\"sender\":\"Opa\",\"recipient\":\"Linus\",\"date\":\"24.04.2021\",\"amount\":50.0,\"description\":\"Geburtstagsgeschenk von Opa\"}}";

        GsonBuilder deserializer = new GsonBuilder();

        deserializer.registerTypeAdapter(Transaction.class, new customDeserializer());

        Gson customDeserializer = deserializer.create();
        Transaction customObject = customDeserializer.fromJson(customJsonX, Transaction.class);
    }
}
