/**
 * This class is generated by jOOQ
 */
package com.dq.DQTRM.model.tables.records;


import com.dq.DQTRM.model.tables.TmRelationship;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TmRelationshipRecord extends TableRecordImpl<TmRelationshipRecord> implements Record8<String, String, String, Integer, String, String, String, String> {

    private static final long serialVersionUID = -513373352;

    /**
     * Setter for <code>DQTRM.TM_RELATIONSHIP.FK_PARENT_TRADEMARK_GID</code>.
     */
    public void setFkParentTrademarkGid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>DQTRM.TM_RELATIONSHIP.FK_PARENT_TRADEMARK_GID</code>.
     */
    public String getFkParentTrademarkGid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>DQTRM.TM_RELATIONSHIP.FK_RELATED_TRADEMARK_GID</code>.
     */
    public void setFkRelatedTrademarkGid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>DQTRM.TM_RELATIONSHIP.FK_RELATED_TRADEMARK_GID</code>.
     */
    public String getFkRelatedTrademarkGid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>DQTRM.TM_RELATIONSHIP.FK_RELATIONSHIP_TYPE_CD</code>.
     */
    public void setFkRelationshipTypeCd(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>DQTRM.TM_RELATIONSHIP.FK_RELATIONSHIP_TYPE_CD</code>.
     */
    public String getFkRelationshipTypeCd() {
        return (String) get(2);
    }

    /**
     * Setter for <code>DQTRM.TM_RELATIONSHIP.LOCK_CONTROL_NO</code>.
     */
    public void setLockControlNo(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>DQTRM.TM_RELATIONSHIP.LOCK_CONTROL_NO</code>.
     */
    public Integer getLockControlNo() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>DQTRM.TM_RELATIONSHIP.CREATE_TS</code>.
     */
    public void setCreateTs(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>DQTRM.TM_RELATIONSHIP.CREATE_TS</code>.
     */
    public String getCreateTs() {
        return (String) get(4);
    }

    /**
     * Setter for <code>DQTRM.TM_RELATIONSHIP.CREATE_USER_ID</code>.
     */
    public void setCreateUserId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>DQTRM.TM_RELATIONSHIP.CREATE_USER_ID</code>.
     */
    public String getCreateUserId() {
        return (String) get(5);
    }

    /**
     * Setter for <code>DQTRM.TM_RELATIONSHIP.LAST_MOD_TS</code>.
     */
    public void setLastModTs(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>DQTRM.TM_RELATIONSHIP.LAST_MOD_TS</code>.
     */
    public String getLastModTs() {
        return (String) get(6);
    }

    /**
     * Setter for <code>DQTRM.TM_RELATIONSHIP.LAST_MOD_USER_ID</code>.
     */
    public void setLastModUserId(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>DQTRM.TM_RELATIONSHIP.LAST_MOD_USER_ID</code>.
     */
    public String getLastModUserId() {
        return (String) get(7);
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<String, String, String, Integer, String, String, String, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<String, String, String, Integer, String, String, String, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return TmRelationship.TM_RELATIONSHIP.FK_PARENT_TRADEMARK_GID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TmRelationship.TM_RELATIONSHIP.FK_RELATED_TRADEMARK_GID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TmRelationship.TM_RELATIONSHIP.FK_RELATIONSHIP_TYPE_CD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return TmRelationship.TM_RELATIONSHIP.LOCK_CONTROL_NO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return TmRelationship.TM_RELATIONSHIP.CREATE_TS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return TmRelationship.TM_RELATIONSHIP.CREATE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return TmRelationship.TM_RELATIONSHIP.LAST_MOD_TS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return TmRelationship.TM_RELATIONSHIP.LAST_MOD_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getFkParentTrademarkGid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getFkRelatedTrademarkGid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getFkRelationshipTypeCd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getLockControlNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getCreateTs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getCreateUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getLastModTs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getLastModUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TmRelationshipRecord value1(String value) {
        setFkParentTrademarkGid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TmRelationshipRecord value2(String value) {
        setFkRelatedTrademarkGid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TmRelationshipRecord value3(String value) {
        setFkRelationshipTypeCd(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TmRelationshipRecord value4(Integer value) {
        setLockControlNo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TmRelationshipRecord value5(String value) {
        setCreateTs(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TmRelationshipRecord value6(String value) {
        setCreateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TmRelationshipRecord value7(String value) {
        setLastModTs(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TmRelationshipRecord value8(String value) {
        setLastModUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TmRelationshipRecord values(String value1, String value2, String value3, Integer value4, String value5, String value6, String value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TmRelationshipRecord
     */
    public TmRelationshipRecord() {
        super(TmRelationship.TM_RELATIONSHIP);
    }

    /**
     * Create a detached, initialised TmRelationshipRecord
     */
    public TmRelationshipRecord(String fkParentTrademarkGid, String fkRelatedTrademarkGid, String fkRelationshipTypeCd, Integer lockControlNo, String createTs, String createUserId, String lastModTs, String lastModUserId) {
        super(TmRelationship.TM_RELATIONSHIP);

        set(0, fkParentTrademarkGid);
        set(1, fkRelatedTrademarkGid);
        set(2, fkRelationshipTypeCd);
        set(3, lockControlNo);
        set(4, createTs);
        set(5, createUserId);
        set(6, lastModTs);
        set(7, lastModUserId);
    }
}