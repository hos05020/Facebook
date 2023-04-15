package com.example.facebookapiserver.domain.common;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Id<R,V> {

    private final Class<R> refer;

    private final V value;

    public Id(Class<R> refer, V value) {
        this.refer = refer;
        this.value = value;
    }


    public static <R,V> Id<R,V> of(Class<R> refer, V value){
        checkArgument(refer!=null,"reference must be provided");
        checkArgument(value!=null,"reference must be provided");
        return new Id<>(refer,value);
    }

    public V value(){return  value;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Id<?, ?> id = (Id<?, ?>) o;
        return Objects.equals(refer, id.refer) && Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refer, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("refer", refer.getSimpleName())
            .append("value", value)
            .toString();
    }
}
