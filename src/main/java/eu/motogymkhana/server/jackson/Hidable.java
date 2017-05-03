package eu.motogymkhana.server.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("hidden")
public interface Hidable {
    boolean isHidden();
}