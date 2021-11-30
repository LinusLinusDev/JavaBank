package bank;

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Klasse, die genutzt werden kann, um Objekte der Klasse {@link Transaction} entsprechend der Anforderungen zu serialisieren und zu deserialisieren.
 *
 * @author Linus Palm
 */
public class customSerializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    /**
     * Funktion, die eine Serialisierung entsprechend der Anforderungen ermöglicht. Differenziert zwischen Transfer und Payment Objekten.
     *
     * @param transaction Das zu serialisierende Objekt der Klasse Transaction
     * @param type Konfigurationsparameter, wird von Gson Objekt gesetzt
     * @param jsonSerializationContext Konfigurationsparameter, wird von Gson Objekt gesetzt
     * @return Serialisiertes JsonElement als String
     */
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject values = new JsonObject();
        JsonObject capsule = new JsonObject();

        if(transaction instanceof Payment){
            values.addProperty("incomingInterest",((Payment) transaction).getIncomingInterest());
            values.addProperty("outgoingInterest",((Payment) transaction).getOutgoingInterest());
        }
        else if(transaction instanceof Transfer) {
            values.addProperty("sender",((Transfer) transaction).getSender());
            values.addProperty("recipient",((Transfer) transaction).getRecipient());
        }
        values.addProperty("date",transaction.getDate());
        values.addProperty("amount",transaction.getAmount());
        values.addProperty("description",transaction.getDescription());

        capsule.addProperty("CLASSNAME",transaction.getClass().getSimpleName());
        capsule.add("INSTANCE",values);
        return capsule;
    }

    /**
     * Funktion, die eine Deserialisierung entsprechend der Anforderungen ermöglicht. Differenziert zwischen Transfer und Payment Objekten.
     *
     * @param jsonElement Das zu deserialisierende JsonElement als String
     * @param type Konfigurationsparameter, wird von Gson Objekt gesetzt
     * @param jsonDeserializationContext Konfigurationsparameter, wird von Gson Objekt gesetzt
     * @return Deserialisiertes Objekt der Klasse Transaction
     * @throws JsonParseException wird geworfen, falls das zu deserialisierende Dokument nicht dem erforderlichem Format entspricht.
     */
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject capsule = jsonElement.getAsJsonObject();
        if(!(capsule.has("CLASSNAME") && capsule.has("INSTANCE")))throw new JsonParseException("Invalid Input. CLASSNAME/INSTANCE missing.");

        JsonObject values = capsule.get("INSTANCE").getAsJsonObject();
        if(!(values.has("date") && values.has("amount") && values.has("description")))throw new JsonParseException("Invalid Input. date/amount/description missing.");

        switch (capsule.get("CLASSNAME").getAsString()){
            case "Payment":
                if(!(values.has("incomingInterest") && values.has("outgoingInterest")))throw new JsonParseException("Invalid Input. incomingInterest/outgoingInterest missing.");
                return new Payment(
                        values.get("date").getAsString(),
                        values.get("amount").getAsDouble(),
                        values.get("description").getAsString(),
                        values.get("incomingInterest").getAsDouble(),
                        values.get("outgoingInterest").getAsDouble()
                );
            case "IncomingTransfer":
                if(!(values.has("sender") && values.has("recipient")))throw new JsonParseException("Invalid Input. sender/recipient missing.");
                return new IncomingTransfer(
                        values.get("date").getAsString(),
                        values.get("amount").getAsDouble(),
                        values.get("description").getAsString(),
                        values.get("sender").getAsString(),
                        values.get("recipient").getAsString()
                );
            case "OutgoingTransfer":
                if(!(values.has("sender") && values.has("recipient")))throw new JsonParseException("Invalid Input. sender/recipient missing.");
                return new OutgoingTransfer(
                        values.get("date").getAsString(),
                        values.get("amount").getAsDouble(),
                        values.get("description").getAsString(),
                        values.get("sender").getAsString(),
                        values.get("recipient").getAsString()
                );
        }
        return null;
    }
}
