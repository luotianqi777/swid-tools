/**
 * Portions of this software was developed by employees of the National Institute
 * of Standards and Technology (NIST), an agency of the Federal Government.
 * Pursuant to title 17 United States Code Section 105, works of NIST employees are
 * not subject to copyright protection in the United States and are considered to
 * be in the public domain. Permission to freely use, copy, modify, and distribute
 * this software and its documentation without fee is hereby granted, provided that
 * this notice and disclaimer of warranty appears in all copies.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS' WITHOUT ANY WARRANTY OF ANY KIND, EITHER
 * EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT LIMITED TO, ANY WARRANTY
 * THAT THE SOFTWARE WILL CONFORM TO SPECIFICATIONS, ANY IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND FREEDOM FROM
 * INFRINGEMENT, AND ANY WARRANTY THAT THE DOCUMENTATION WILL CONFORM TO THE
 * SOFTWARE, OR ANY WARRANTY THAT THE SOFTWARE WILL BE ERROR FREE. IN NO EVENT
 * SHALL NIST BE LIABLE FOR ANY DAMAGES, INCLUDING, BUT NOT LIMITED TO, DIRECT,
 * INDIRECT, SPECIAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF, RESULTING FROM, OR
 * IN ANY WAY CONNECTED WITH THIS SOFTWARE, WHETHER OR NOT BASED UPON WARRANTY,
 * CONTRACT, TORT, OR OTHERWISE, WHETHER OR NOT INJURY WAS SUSTAINED BY PERSONS OR
 * PROPERTY OR OTHERWISE, AND WHETHER OR NOT LOSS WAS SUSTAINED FROM, OR AROSE OUT
 * OF THE RESULTS OF, OR USE OF, THE SOFTWARE OR SERVICES PROVIDED HEREUNDER.
 */

package gov.nist.swid.builder;

import static gov.nist.swid.builder.util.Util.requireNonEmpty;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SWIDBuilder extends AbstractBuilder<SWIDBuilder> {
    private TagType tagType = TagType.PRIMARY;
    private String name;
    private String tagId;
    private int tagVersion = SWIDConstants.TAG_VERSION_DEFAULT;
    private String version;
    private String versionScheme;
    private List<EntityBuilder> entities = new LinkedList<>();
    private EvidenceBuilder evidence;
    private List<LinkBuilder> links = new LinkedList<>();
    private List<MetaBuilder> metas = new LinkedList<>();
    private PayloadBuilder payload;
    private String media;

    protected SWIDBuilder() {
        super();
    }

    @Override
    public void reset() {
        super.reset();

        language(Locale.getDefault().toLanguageTag());

        this.tagType = TagType.PRIMARY;
        this.name = null;
        this.tagId = null;
        this.tagVersion = SWIDConstants.TAG_VERSION_DEFAULT;
        this.version = null;
        this.versionScheme = null;
        this.entities = new LinkedList<>();
        ;
        this.evidence = null;
        this.links = new LinkedList<>();
        ;
        this.metas = new LinkedList<>();
        ;
        this.payload = null;
        this.media = null;
    }

    public static SWIDBuilder create() {
        return new SWIDBuilder();
    }

    public String getTagId() {
        return tagId;
    }

    public TagType getTagType() {
        return tagType;
    }

    public String getName() {
        return name;
    }

    public int getTagVersion() {
        return tagVersion;
    }

    public String getVersion() {
        return (version == null ? SWIDConstants.VERSION_DEFAULT : version);
    }

    public String getVersionScheme() {
        return (versionScheme == null ? SWIDConstants.VERSION_SCHEME_DEFAULT : versionScheme);
    }

    public List<EntityBuilder> getEntities() {
        return entities;
    }

    public EvidenceBuilder getEvidence() {
        return evidence;
    }

    public PayloadBuilder getPayload() {
        return payload;
    }

    public EvidenceBuilder newEvidence() {
        if (evidence == null) {
            evidence = EvidenceBuilder.create();
        }
        return evidence;
    }

    public List<LinkBuilder> getLinks() {
        return links;
    }

    public List<MetaBuilder> getMetas() {
        return metas;
    }

    /**
     * Retrieves the existing PayloadBuilder or creates a new one if no PayloadBuilder has been
     * created already.
     * 
     * @return the payload builder
     */
    public PayloadBuilder newPayload() {
        if (payload == null) {
            payload = PayloadBuilder.create();
        }
        return payload;
    }

    public String getMedia() {
        return media;
    }

    /**
     * Sets the to-be-built tag's product type to the provided value.
     * 
     * @param type
     *            the new type to set
     * @return the same builder instance
     */
    public SWIDBuilder tagType(TagType type) {
        Objects.requireNonNull(type, "tagType");
        this.tagType = type;
        return this;
    }

    /**
     * Sets the to-be-built tag's product name to the provided value.
     * 
     * @param name
     *            the name of the software product
     * @return the same builder instance
     */
    public SWIDBuilder name(String name) {
        requireNonEmpty(name, "name");
        this.name = name;
        return this;
    }

    /**
     * Sets the to-be-built tag's product tag identifier to the provided value.
     * 
     * @param id
     *            the tag identifier for the software product
     * @return the same builder instance
     */
    public SWIDBuilder tagId(String id) {
        requireNonEmpty(id, "id");
        this.tagId = id;
        return this;
    }

    public SWIDBuilder tagVersion(int version) {
        this.tagVersion = version;
        return this;
    }

    /**
     * Sets the to-be-built SWID tag's version to the provided value.
     * 
     * @param version
     *            the version value to use
     * @return the same builder instance
     */
    public SWIDBuilder version(String version) {
        requireNonEmpty(version, "version");
        if (SWIDConstants.VERSION_DEFAULT.equals(version)) {
            this.version = null;
        } else {
            this.version = version;
        }
        return this;
    }

    /**
     * Sets the to-be-built SWID tag's versionSchema to the provided value. The version scheme
     * identifies the structure of the provided version.
     * 
     * @param scheme
     *            the version scheme for the tag
     * @return the same builder instance
     * @see #version(String)
     */
    public SWIDBuilder versionScheme(String scheme) {
        requireNonEmpty(scheme, "versionScheme");
        if (SWIDConstants.VERSION_DEFAULT.equals(scheme)) {
            this.versionScheme = null;
        } else {
            this.versionScheme = scheme;
        }
        return this;
    }

    /**
     * Sets the to-be-built SWID tag's media to the provided value.
     * 
     * @param media
     *            the media value to use
     * @return the same builder instance
     */
    public SWIDBuilder media(String media) {
        Objects.requireNonNull(media, "media");
        this.media = media;
        return this;
    }

    /**
     * Adds a new entity to the tag.
     * 
     * @param entity
     *            a entity builder representing the new entity to add
     * @return the same builder instance
     */
    public SWIDBuilder addEntity(EntityBuilder entity) {
        Objects.requireNonNull(entity, "entity");
        this.entities.add(entity);
        return this;
    }

    /**
     * Adds a new link to the tag.
     * 
     * @param link
     *            a link builder representing the new link to add
     * @return the same builder instance
     */
    public SWIDBuilder addLink(LinkBuilder link) {
        Objects.requireNonNull(link, "link");
        this.links.add(link);
        return this;
    }

    /**
     * Adds a new meta to the tag.
     * 
     * @param meta
     *            a meta builder representing the new meta to add
     * @return the same builder instance
     */
    public SWIDBuilder addMeta(MetaBuilder meta) {
        Objects.requireNonNull(meta, "meta");
        this.metas.add(meta);
        return this;
    }

    /**
     * Adds a new payload to the tag.
     * 
     * @param payload
     *            a payload builder representing the new payload to add
     * @return the same builder instance
     */
    public SWIDBuilder payload(PayloadBuilder payload) {
        Objects.requireNonNull(payload, "payload");
        this.payload = payload;
        return this;
    }

    /**
     * Adds a new evidence to the tag.
     * 
     * @param evidence
     *            a evidence builder representing the new evidence to add
     * @return the same builder instance
     */
    public SWIDBuilder evidence(EvidenceBuilder evidence) {
        Objects.requireNonNull(evidence, "evidence");
        this.evidence = evidence;
        return this;
    }

    @Override
    public boolean isValid() {
        boolean retval = (name != null && tagId != null);
        if (retval) {
            retval = !entities.isEmpty() && entities.stream().allMatch(e -> e.isValid());
        }

        if (retval) {
            retval = evidence == null || evidence.isValid();
        }

        if (retval) {
            retval = links.isEmpty() || links.stream().allMatch(e -> e.isValid());
        }

        if (retval) {
            retval = metas.isEmpty() || metas.stream().allMatch(e -> e.isValid());
        }

        if (retval) {
            retval = payload == null || payload.isValid();
        }
        return retval;
    }

    @Override
    public void validate() {
        requireNonEmpty(name, "name");
        requireNonEmpty(tagId, "tagId");
        if (entities.isEmpty()) {
            throw new IllegalStateException("at least a single entity must be provided");
        }
        entities.stream().forEach(e -> e.validate());

        if (payload != null && evidence != null) {
            throw new IllegalStateException("evidence and payload cannot be both provided");
        }

        if (payload != null) {
            payload.validate();
        }

        if (evidence != null) {
            evidence.validate();
        }

        if (!links.isEmpty()) {
            links.stream().forEach(e -> e.validate());
        }

        if (!metas.isEmpty()) {
            metas.stream().forEach(e -> e.validate());
        }
    }

}
