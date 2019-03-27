package org.shanoir.ng.shared.hateoas;

import org.shanoir.ng.shared.core.model.AbstractEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Helps to format an entity to the HAL format
 *
 * @author jlouis
 */
public abstract class HalEntity extends AbstractEntity {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -8580796056437763225L;

	@JsonIgnore
	private Links links = new Links();

	/**
	 * @return the links
	 */
	@JsonProperty("_links")
	public Links getLinks() {
		return links;
	}

	/**
	 * @param links
	 *            the links to set
	 */
	@JsonIgnore
	public void setLinks(Links links) {
		this.links = links;
	}

	public void addLink(String key, String href) {
		links.put(key, new Link(key, Links.BASE_URL + href));
	}

}
