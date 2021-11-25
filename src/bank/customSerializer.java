package bank;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Klasse, die genutzt werden kann, um Objekte der Klasse {@link Transaction} entsprechend der Anforderungen zu serialisieren.
 *
 * @author Linus Palm
 */
public class customSerializer implements JsonSerializer<Transaction> {

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
}
