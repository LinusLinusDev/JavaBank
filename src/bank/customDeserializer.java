package bank;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Klasse, die genutzt werden kann, um Objekte der Klasse {@link Transaction} entsprechend der Anforderungen zu deserialisieren.
 *
 * @author Linus Palm
 */
public class customDeserializer implements JsonDeserializer<Transaction>{

    /**
     * Funktion, die eine Deserialisierung entsprechend der Anforderungen ermöglicht. Differenziert zwischen Transfer und Payment Objekten.
     *
     * @param jsonElement Das zu deserialisierende JsonElement als String
     * @param type Konfigurationsparameter, wird von Gson Objekt gesetzt
     * @param jsonDeserializationContext Konfigurationsparameter, wird von Gson Objekt gesetzt
     * @return Deserialisiertes Objekt der Klasse Transaction
     * @throws JsonParseException
     */
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject capsule = jsonElement.getAsJsonObject();
        JsonObject values = capsule.get("INSTANCE").getAsJsonObject();
        switch (capsule.get("CLASSNAME").getAsString()){
            case "Payment":
                return new Payment(
                        values.get("date").getAsString(),
                        values.get("amount").getAsDouble(),
                        values.get("description").getAsString(),
                        values.get("incomingInterest").getAsDouble(),
                        values.get("outgoingInterest").getAsDouble()
                );
            case "IncomingTransfer":
                return new IncomingTransfer(
                        values.get("date").getAsString(),
                        values.get("amount").getAsDouble(),
                        values.get("description").getAsString(),
                        values.get("sender").getAsString(),
                        values.get("recipient").getAsString()
                );
            case "OutgoingTransfer":
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
