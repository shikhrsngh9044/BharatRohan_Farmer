package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class States {

    @SerializedName("data")
    List<State> state;


    public States(List<State> state) {
        this.state = state;
    }

    public List<State> getState() {
        return state;
    }

    public class State {
        @SerializedName("_id")
        private String id;
        @SerializedName("state_name")
        private String name;


        public State(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
