/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.onlineinteract.workflow.domain.account.v1;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Account extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6722975085895331121L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Account\",\"namespace\":\"com.onlineinteract.workflow.domain.account.v1\",\"fields\":[{\"name\":\"eventId\",\"type\":\"string\"},{\"name\":\"created\",\"type\":\"int\"},{\"name\":\"eventType\",\"type\":\"string\"},{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"type\",\"type\":\"string\"},{\"name\":\"openingBalance\",\"type\":\"string\"},{\"name\":\"savingsRate\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence eventId;
  @Deprecated public int created;
  @Deprecated public java.lang.CharSequence eventType;
  @Deprecated public java.lang.CharSequence id;
  @Deprecated public java.lang.CharSequence name;
  @Deprecated public java.lang.CharSequence type;
  @Deprecated public java.lang.CharSequence openingBalance;
  @Deprecated public java.lang.CharSequence savingsRate;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Account() {}

  /**
   * All-args constructor.
   * @param eventId The new value for eventId
   * @param created The new value for created
   * @param eventType The new value for eventType
   * @param id The new value for id
   * @param name The new value for name
   * @param type The new value for type
   * @param openingBalance The new value for openingBalance
   * @param savingsRate The new value for savingsRate
   */
  public Account(java.lang.CharSequence eventId, java.lang.Integer created, java.lang.CharSequence eventType, java.lang.CharSequence id, java.lang.CharSequence name, java.lang.CharSequence type, java.lang.CharSequence openingBalance, java.lang.CharSequence savingsRate) {
    this.eventId = eventId;
    this.created = created;
    this.eventType = eventType;
    this.id = id;
    this.name = name;
    this.type = type;
    this.openingBalance = openingBalance;
    this.savingsRate = savingsRate;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return eventId;
    case 1: return created;
    case 2: return eventType;
    case 3: return id;
    case 4: return name;
    case 5: return type;
    case 6: return openingBalance;
    case 7: return savingsRate;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: eventId = (java.lang.CharSequence)value$; break;
    case 1: created = (java.lang.Integer)value$; break;
    case 2: eventType = (java.lang.CharSequence)value$; break;
    case 3: id = (java.lang.CharSequence)value$; break;
    case 4: name = (java.lang.CharSequence)value$; break;
    case 5: type = (java.lang.CharSequence)value$; break;
    case 6: openingBalance = (java.lang.CharSequence)value$; break;
    case 7: savingsRate = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'eventId' field.
   * @return The value of the 'eventId' field.
   */
  public java.lang.CharSequence getEventId() {
    return eventId;
  }

  /**
   * Sets the value of the 'eventId' field.
   * @param value the value to set.
   */
  public void setEventId(java.lang.CharSequence value) {
    this.eventId = value;
  }

  /**
   * Gets the value of the 'created' field.
   * @return The value of the 'created' field.
   */
  public java.lang.Integer getCreated() {
    return created;
  }

  /**
   * Sets the value of the 'created' field.
   * @param value the value to set.
   */
  public void setCreated(java.lang.Integer value) {
    this.created = value;
  }

  /**
   * Gets the value of the 'eventType' field.
   * @return The value of the 'eventType' field.
   */
  public java.lang.CharSequence getEventType() {
    return eventType;
  }

  /**
   * Sets the value of the 'eventType' field.
   * @param value the value to set.
   */
  public void setEventType(java.lang.CharSequence value) {
    this.eventType = value;
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.CharSequence getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.CharSequence value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public java.lang.CharSequence getName() {
    return name;
  }

  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.CharSequence value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'type' field.
   * @return The value of the 'type' field.
   */
  public java.lang.CharSequence getType() {
    return type;
  }

  /**
   * Sets the value of the 'type' field.
   * @param value the value to set.
   */
  public void setType(java.lang.CharSequence value) {
    this.type = value;
  }

  /**
   * Gets the value of the 'openingBalance' field.
   * @return The value of the 'openingBalance' field.
   */
  public java.lang.CharSequence getOpeningBalance() {
    return openingBalance;
  }

  /**
   * Sets the value of the 'openingBalance' field.
   * @param value the value to set.
   */
  public void setOpeningBalance(java.lang.CharSequence value) {
    this.openingBalance = value;
  }

  /**
   * Gets the value of the 'savingsRate' field.
   * @return The value of the 'savingsRate' field.
   */
  public java.lang.CharSequence getSavingsRate() {
    return savingsRate;
  }

  /**
   * Sets the value of the 'savingsRate' field.
   * @param value the value to set.
   */
  public void setSavingsRate(java.lang.CharSequence value) {
    this.savingsRate = value;
  }

  /**
   * Creates a new Account RecordBuilder.
   * @return A new Account RecordBuilder
   */
  public static com.onlineinteract.workflow.domain.account.v1.Account.Builder newBuilder() {
    return new com.onlineinteract.workflow.domain.account.v1.Account.Builder();
  }

  /**
   * Creates a new Account RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Account RecordBuilder
   */
  public static com.onlineinteract.workflow.domain.account.v1.Account.Builder newBuilder(com.onlineinteract.workflow.domain.account.v1.Account.Builder other) {
    return new com.onlineinteract.workflow.domain.account.v1.Account.Builder(other);
  }

  /**
   * Creates a new Account RecordBuilder by copying an existing Account instance.
   * @param other The existing instance to copy.
   * @return A new Account RecordBuilder
   */
  public static com.onlineinteract.workflow.domain.account.v1.Account.Builder newBuilder(com.onlineinteract.workflow.domain.account.v1.Account other) {
    return new com.onlineinteract.workflow.domain.account.v1.Account.Builder(other);
  }

  /**
   * RecordBuilder for Account instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Account>
    implements org.apache.avro.data.RecordBuilder<Account> {

    private java.lang.CharSequence eventId;
    private int created;
    private java.lang.CharSequence eventType;
    private java.lang.CharSequence id;
    private java.lang.CharSequence name;
    private java.lang.CharSequence type;
    private java.lang.CharSequence openingBalance;
    private java.lang.CharSequence savingsRate;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.onlineinteract.workflow.domain.account.v1.Account.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.eventId)) {
        this.eventId = data().deepCopy(fields()[0].schema(), other.eventId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.created)) {
        this.created = data().deepCopy(fields()[1].schema(), other.created);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.eventType)) {
        this.eventType = data().deepCopy(fields()[2].schema(), other.eventType);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.id)) {
        this.id = data().deepCopy(fields()[3].schema(), other.id);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.name)) {
        this.name = data().deepCopy(fields()[4].schema(), other.name);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.type)) {
        this.type = data().deepCopy(fields()[5].schema(), other.type);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.openingBalance)) {
        this.openingBalance = data().deepCopy(fields()[6].schema(), other.openingBalance);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.savingsRate)) {
        this.savingsRate = data().deepCopy(fields()[7].schema(), other.savingsRate);
        fieldSetFlags()[7] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Account instance
     * @param other The existing instance to copy.
     */
    private Builder(com.onlineinteract.workflow.domain.account.v1.Account other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.eventId)) {
        this.eventId = data().deepCopy(fields()[0].schema(), other.eventId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.created)) {
        this.created = data().deepCopy(fields()[1].schema(), other.created);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.eventType)) {
        this.eventType = data().deepCopy(fields()[2].schema(), other.eventType);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.id)) {
        this.id = data().deepCopy(fields()[3].schema(), other.id);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.name)) {
        this.name = data().deepCopy(fields()[4].schema(), other.name);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.type)) {
        this.type = data().deepCopy(fields()[5].schema(), other.type);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.openingBalance)) {
        this.openingBalance = data().deepCopy(fields()[6].schema(), other.openingBalance);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.savingsRate)) {
        this.savingsRate = data().deepCopy(fields()[7].schema(), other.savingsRate);
        fieldSetFlags()[7] = true;
      }
    }

    /**
      * Gets the value of the 'eventId' field.
      * @return The value.
      */
    public java.lang.CharSequence getEventId() {
      return eventId;
    }

    /**
      * Sets the value of the 'eventId' field.
      * @param value The value of 'eventId'.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder setEventId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.eventId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'eventId' field has been set.
      * @return True if the 'eventId' field has been set, false otherwise.
      */
    public boolean hasEventId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'eventId' field.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder clearEventId() {
      eventId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'created' field.
      * @return The value.
      */
    public java.lang.Integer getCreated() {
      return created;
    }

    /**
      * Sets the value of the 'created' field.
      * @param value The value of 'created'.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder setCreated(int value) {
      validate(fields()[1], value);
      this.created = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'created' field has been set.
      * @return True if the 'created' field has been set, false otherwise.
      */
    public boolean hasCreated() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'created' field.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder clearCreated() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'eventType' field.
      * @return The value.
      */
    public java.lang.CharSequence getEventType() {
      return eventType;
    }

    /**
      * Sets the value of the 'eventType' field.
      * @param value The value of 'eventType'.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder setEventType(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.eventType = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'eventType' field has been set.
      * @return True if the 'eventType' field has been set, false otherwise.
      */
    public boolean hasEventType() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'eventType' field.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder clearEventType() {
      eventType = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.CharSequence getId() {
      return id;
    }

    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder setId(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.id = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder clearId() {
      id = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.CharSequence getName() {
      return name;
    }

    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder setName(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.name = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder clearName() {
      name = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'type' field.
      * @return The value.
      */
    public java.lang.CharSequence getType() {
      return type;
    }

    /**
      * Sets the value of the 'type' field.
      * @param value The value of 'type'.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder setType(java.lang.CharSequence value) {
      validate(fields()[5], value);
      this.type = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'type' field has been set.
      * @return True if the 'type' field has been set, false otherwise.
      */
    public boolean hasType() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'type' field.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder clearType() {
      type = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'openingBalance' field.
      * @return The value.
      */
    public java.lang.CharSequence getOpeningBalance() {
      return openingBalance;
    }

    /**
      * Sets the value of the 'openingBalance' field.
      * @param value The value of 'openingBalance'.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder setOpeningBalance(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.openingBalance = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'openingBalance' field has been set.
      * @return True if the 'openingBalance' field has been set, false otherwise.
      */
    public boolean hasOpeningBalance() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'openingBalance' field.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder clearOpeningBalance() {
      openingBalance = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'savingsRate' field.
      * @return The value.
      */
    public java.lang.CharSequence getSavingsRate() {
      return savingsRate;
    }

    /**
      * Sets the value of the 'savingsRate' field.
      * @param value The value of 'savingsRate'.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder setSavingsRate(java.lang.CharSequence value) {
      validate(fields()[7], value);
      this.savingsRate = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'savingsRate' field has been set.
      * @return True if the 'savingsRate' field has been set, false otherwise.
      */
    public boolean hasSavingsRate() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'savingsRate' field.
      * @return This builder.
      */
    public com.onlineinteract.workflow.domain.account.v1.Account.Builder clearSavingsRate() {
      savingsRate = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    @Override
    public Account build() {
      try {
        Account record = new Account();
        record.eventId = fieldSetFlags()[0] ? this.eventId : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.created = fieldSetFlags()[1] ? this.created : (java.lang.Integer) defaultValue(fields()[1]);
        record.eventType = fieldSetFlags()[2] ? this.eventType : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.id = fieldSetFlags()[3] ? this.id : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.name = fieldSetFlags()[4] ? this.name : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.type = fieldSetFlags()[5] ? this.type : (java.lang.CharSequence) defaultValue(fields()[5]);
        record.openingBalance = fieldSetFlags()[6] ? this.openingBalance : (java.lang.CharSequence) defaultValue(fields()[6]);
        record.savingsRate = fieldSetFlags()[7] ? this.savingsRate : (java.lang.CharSequence) defaultValue(fields()[7]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}