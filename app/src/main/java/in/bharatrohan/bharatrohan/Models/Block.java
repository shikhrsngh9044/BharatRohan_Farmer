package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Block {

    @SerializedName("blocks")
    private List<Blocks> blocks;


    public Block(List<Blocks> blocks) {
        this.blocks = blocks;
    }

    public List<Blocks> getBlocks() {
        return blocks;
    }

    public class Blocks{
        @SerializedName("_id")
        private String id;
        @SerializedName("block_name")
        private String name;


        public Blocks(String id, String name) {
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
