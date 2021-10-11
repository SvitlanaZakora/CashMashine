package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private int id;
    private String short_name;
    private String name;

    public static Builder builder() {
        return new Role().new Builder();
    }
    public class Builder {

        private int id;
        private String short_name;
        private String name;

        public Builder id(int id) {
            this.id = id;

            return this;
        }

        public Builder short_name(String short_name) {
            this.short_name = short_name;

            return this;
        }

        public Builder name(String name) {
            this.name = name;

            return this;
        }

        public Role build() {
            return new Role(id, short_name, name);
        }
    }
}
