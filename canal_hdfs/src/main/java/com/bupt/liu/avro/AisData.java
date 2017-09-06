package com.bupt.liu.avro;

/**
 * Created by lpeiz on 2017/9/2.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.apache.avro.AvroRuntimeException;
import org.apache.avro.Schema;
import org.apache.avro.data.RecordBuilder;
import org.apache.avro.specific.AvroGenerated;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.avro.specific.SpecificRecordBuilderBase;

import java.util.Map;


@AvroGenerated
public class AisData extends SpecificRecordBase implements SpecificRecord {
    public static final Schema SCHEMA$ = (new Schema.Parser()).parse("{\"type\":\"record\",\"name\":\"AisData\",\"namespace\":\"com.jd.bdp.jdw.avro\",\"fields\":[{\"name\":\"mid\",\"type\":\"long\"},{\"name\":\"db\",\"type\":\"string\"},{\"name\":\"sch\",\"type\":\"string\"},{\"name\":\"tab\",\"type\":\"string\"},{\"name\":\"opt\",\"type\":\"string\"},{\"name\":\"ts\",\"type\":\"long\"},{\"name\":\"ddl\",\"type\":[\"string\",\"null\"]},{\"name\":\"err\",\"type\":[\"string\",\"null\"]},{\"name\":\"src\",\"type\":[{\"type\":\"map\",\"values\":[\"string\",\"null\"]},\"null\"]},{\"name\":\"cur\",\"type\":[{\"type\":\"map\",\"values\":[\"string\",\"null\"]},\"null\"]},{\"name\":\"cus\",\"type\":[{\"type\":\"map\",\"values\":[\"string\",\"null\"]},\"null\"]}]}");
    /** @deprecated */
    @Deprecated
    public long mid;
    /** @deprecated */
    @Deprecated
    public CharSequence db;
    /** @deprecated */
    @Deprecated
    public CharSequence sch;
    /** @deprecated */
    @Deprecated
    public CharSequence tab;
    /** @deprecated */
    @Deprecated
    public CharSequence opt;
    /** @deprecated */
    @Deprecated
    public long ts;
    /** @deprecated */
    @Deprecated
    public CharSequence ddl;
    /** @deprecated */
    @Deprecated
    public CharSequence err;
    /** @deprecated */
    @Deprecated
    public Map<CharSequence, CharSequence> src;
    /** @deprecated */
    @Deprecated
    public Map<CharSequence, CharSequence> cur;
    /** @deprecated */
    @Deprecated
    public Map<CharSequence, CharSequence> cus;

    public static Schema getClassSchema() {
        return SCHEMA$;
    }

    public AisData() {
    }

    public AisData(Long mid, CharSequence db, CharSequence sch, CharSequence tab, CharSequence opt, Long ts, CharSequence ddl, CharSequence err, Map<CharSequence, CharSequence> src, Map<CharSequence, CharSequence> cur, Map<CharSequence, CharSequence> cus) {
        this.mid = mid.longValue();
        this.db = db;
        this.sch = sch;
        this.tab = tab;
        this.opt = opt;
        this.ts = ts.longValue();
        this.ddl = ddl;
        this.err = err;
        this.src = src;
        this.cur = cur;
        this.cus = cus;
    }

    public Schema getSchema() {
        return SCHEMA$;
    }

    public Object get(int field$) {
        switch(field$) {
            case 0:
                return Long.valueOf(this.mid);
            case 1:
                return this.db;
            case 2:
                return this.sch;
            case 3:
                return this.tab;
            case 4:
                return this.opt;
            case 5:
                return Long.valueOf(this.ts);
            case 6:
                return this.ddl;
            case 7:
                return this.err;
            case 8:
                return this.src;
            case 9:
                return this.cur;
            case 10:
                return this.cus;
            default:
                throw new AvroRuntimeException("Bad index");
        }
    }

    public void put(int field$, Object value$) {
        switch(field$) {
            case 0:
                this.mid = ((Long)value$).longValue();
                break;
            case 1:
                this.db = (CharSequence)value$;
                break;
            case 2:
                this.sch = (CharSequence)value$;
                break;
            case 3:
                this.tab = (CharSequence)value$;
                break;
            case 4:
                this.opt = (CharSequence)value$;
                break;
            case 5:
                this.ts = ((Long)value$).longValue();
                break;
            case 6:
                this.ddl = (CharSequence)value$;
                break;
            case 7:
                this.err = (CharSequence)value$;
                break;
            case 8:
                this.src = (Map)value$;
                break;
            case 9:
                this.cur = (Map)value$;
                break;
            case 10:
                this.cus = (Map)value$;
                break;
            default:
                throw new AvroRuntimeException("Bad index");
        }

    }

    public Long getMid() {
        return Long.valueOf(this.mid);
    }

    public void setMid(Long value) {
        this.mid = value.longValue();
    }

    public CharSequence getDb() {
        return this.db;
    }

    public void setDb(CharSequence value) {
        this.db = value;
    }

    public CharSequence getSch() {
        return this.sch;
    }

    public void setSch(CharSequence value) {
        this.sch = value;
    }

    public CharSequence getTab() {
        return this.tab;
    }

    public void setTab(CharSequence value) {
        this.tab = value;
    }

    public CharSequence getOpt() {
        return this.opt;
    }

    public void setOpt(CharSequence value) {
        this.opt = value;
    }

    public Long getTs() {
        return Long.valueOf(this.ts);
    }

    public void setTs(Long value) {
        this.ts = value.longValue();
    }

    public CharSequence getDdl() {
        return this.ddl;
    }

    public void setDdl(CharSequence value) {
        this.ddl = value;
    }

    public CharSequence getErr() {
        return this.err;
    }

    public void setErr(CharSequence value) {
        this.err = value;
    }

    public Map<CharSequence, CharSequence> getSrc() {
        return this.src;
    }

    public void setSrc(Map<CharSequence, CharSequence> value) {
        this.src = value;
    }

    public Map<CharSequence, CharSequence> getCur() {
        return this.cur;
    }

    public void setCur(Map<CharSequence, CharSequence> value) {
        this.cur = value;
    }

    public Map<CharSequence, CharSequence> getCus() {
        return this.cus;
    }

    public void setCus(Map<CharSequence, CharSequence> value) {
        this.cus = value;
    }

    public static AisData.Builder newBuilder() {
        return new AisData.Builder();
    }

    public static AisData.Builder newBuilder(AisData.Builder other) {
        return new AisData.Builder(other);
    }

    public static AisData.Builder newBuilder(AisData other) {
        return new AisData.Builder(other);
    }

    public static class Builder extends SpecificRecordBuilderBase<AisData> implements RecordBuilder<AisData> {
        private long mid;
        private CharSequence db;
        private CharSequence sch;
        private CharSequence tab;
        private CharSequence opt;
        private long ts;
        private CharSequence ddl;
        private CharSequence err;
        private Map<CharSequence, CharSequence> src;
        private Map<CharSequence, CharSequence> cur;
        private Map<CharSequence, CharSequence> cus;

        private Builder() {
            super(AisData.SCHEMA$);
        }

        private Builder(AisData.Builder other) {
            super(other);
        }

        private Builder(AisData other) {
            super(AisData.SCHEMA$);
            if(isValidValue(this.fields()[0], Long.valueOf(other.mid))) {
                this.mid = ((Long)this.data().deepCopy(this.fields()[0].schema(), Long.valueOf(other.mid))).longValue();
                this.fieldSetFlags()[0] = true;
            }

            if(isValidValue(this.fields()[1], other.db)) {
                this.db = (CharSequence)this.data().deepCopy(this.fields()[1].schema(), other.db);
                this.fieldSetFlags()[1] = true;
            }

            if(isValidValue(this.fields()[2], other.sch)) {
                this.sch = (CharSequence)this.data().deepCopy(this.fields()[2].schema(), other.sch);
                this.fieldSetFlags()[2] = true;
            }

            if(isValidValue(this.fields()[3], other.tab)) {
                this.tab = (CharSequence)this.data().deepCopy(this.fields()[3].schema(), other.tab);
                this.fieldSetFlags()[3] = true;
            }

            if(isValidValue(this.fields()[4], other.opt)) {
                this.opt = (CharSequence)this.data().deepCopy(this.fields()[4].schema(), other.opt);
                this.fieldSetFlags()[4] = true;
            }

            if(isValidValue(this.fields()[5], Long.valueOf(other.ts))) {
                this.ts = ((Long)this.data().deepCopy(this.fields()[5].schema(), Long.valueOf(other.ts))).longValue();
                this.fieldSetFlags()[5] = true;
            }

            if(isValidValue(this.fields()[6], other.ddl)) {
                this.ddl = (CharSequence)this.data().deepCopy(this.fields()[6].schema(), other.ddl);
                this.fieldSetFlags()[6] = true;
            }

            if(isValidValue(this.fields()[7], other.err)) {
                this.err = (CharSequence)this.data().deepCopy(this.fields()[7].schema(), other.err);
                this.fieldSetFlags()[7] = true;
            }

            if(isValidValue(this.fields()[8], other.src)) {
                this.src = (Map)this.data().deepCopy(this.fields()[8].schema(), other.src);
                this.fieldSetFlags()[8] = true;
            }

            if(isValidValue(this.fields()[9], other.cur)) {
                this.cur = (Map)this.data().deepCopy(this.fields()[9].schema(), other.cur);
                this.fieldSetFlags()[9] = true;
            }

            if(isValidValue(this.fields()[10], other.cus)) {
                this.cus = (Map)this.data().deepCopy(this.fields()[10].schema(), other.cus);
                this.fieldSetFlags()[10] = true;
            }

        }

        public Long getMid() {
            return Long.valueOf(this.mid);
        }

        public AisData.Builder setMid(long value) {
            this.validate(this.fields()[0], Long.valueOf(value));
            this.mid = value;
            this.fieldSetFlags()[0] = true;
            return this;
        }

        public boolean hasMid() {
            return this.fieldSetFlags()[0];
        }

        public AisData.Builder clearMid() {
            this.fieldSetFlags()[0] = false;
            return this;
        }

        public CharSequence getDb() {
            return this.db;
        }

        public AisData.Builder setDb(CharSequence value) {
            this.validate(this.fields()[1], value);
            this.db = value;
            this.fieldSetFlags()[1] = true;
            return this;
        }

        public boolean hasDb() {
            return this.fieldSetFlags()[1];
        }

        public AisData.Builder clearDb() {
            this.db = null;
            this.fieldSetFlags()[1] = false;
            return this;
        }

        public CharSequence getSch() {
            return this.sch;
        }

        public AisData.Builder setSch(CharSequence value) {
            this.validate(this.fields()[2], value);
            this.sch = value;
            this.fieldSetFlags()[2] = true;
            return this;
        }

        public boolean hasSch() {
            return this.fieldSetFlags()[2];
        }

        public AisData.Builder clearSch() {
            this.sch = null;
            this.fieldSetFlags()[2] = false;
            return this;
        }

        public CharSequence getTab() {
            return this.tab;
        }

        public AisData.Builder setTab(CharSequence value) {
            this.validate(this.fields()[3], value);
            this.tab = value;
            this.fieldSetFlags()[3] = true;
            return this;
        }

        public boolean hasTab() {
            return this.fieldSetFlags()[3];
        }

        public AisData.Builder clearTab() {
            this.tab = null;
            this.fieldSetFlags()[3] = false;
            return this;
        }

        public CharSequence getOpt() {
            return this.opt;
        }

        public AisData.Builder setOpt(CharSequence value) {
            this.validate(this.fields()[4], value);
            this.opt = value;
            this.fieldSetFlags()[4] = true;
            return this;
        }

        public boolean hasOpt() {
            return this.fieldSetFlags()[4];
        }

        public AisData.Builder clearOpt() {
            this.opt = null;
            this.fieldSetFlags()[4] = false;
            return this;
        }

        public Long getTs() {
            return Long.valueOf(this.ts);
        }

        public AisData.Builder setTs(long value) {
            this.validate(this.fields()[5], Long.valueOf(value));
            this.ts = value;
            this.fieldSetFlags()[5] = true;
            return this;
        }

        public boolean hasTs() {
            return this.fieldSetFlags()[5];
        }

        public AisData.Builder clearTs() {
            this.fieldSetFlags()[5] = false;
            return this;
        }

        public CharSequence getDdl() {
            return this.ddl;
        }

        public AisData.Builder setDdl(CharSequence value) {
            this.validate(this.fields()[6], value);
            this.ddl = value;
            this.fieldSetFlags()[6] = true;
            return this;
        }

        public boolean hasDdl() {
            return this.fieldSetFlags()[6];
        }

        public AisData.Builder clearDdl() {
            this.ddl = null;
            this.fieldSetFlags()[6] = false;
            return this;
        }

        public CharSequence getErr() {
            return this.err;
        }

        public AisData.Builder setErr(CharSequence value) {
            this.validate(this.fields()[7], value);
            this.err = value;
            this.fieldSetFlags()[7] = true;
            return this;
        }

        public boolean hasErr() {
            return this.fieldSetFlags()[7];
        }

        public AisData.Builder clearErr() {
            this.err = null;
            this.fieldSetFlags()[7] = false;
            return this;
        }

        public Map<CharSequence, CharSequence> getSrc() {
            return this.src;
        }

        public AisData.Builder setSrc(Map<CharSequence, CharSequence> value) {
            this.validate(this.fields()[8], value);
            this.src = value;
            this.fieldSetFlags()[8] = true;
            return this;
        }

        public boolean hasSrc() {
            return this.fieldSetFlags()[8];
        }

        public AisData.Builder clearSrc() {
            this.src = null;
            this.fieldSetFlags()[8] = false;
            return this;
        }

        public Map<CharSequence, CharSequence> getCur() {
            return this.cur;
        }

        public AisData.Builder setCur(Map<CharSequence, CharSequence> value) {
            this.validate(this.fields()[9], value);
            this.cur = value;
            this.fieldSetFlags()[9] = true;
            return this;
        }

        public boolean hasCur() {
            return this.fieldSetFlags()[9];
        }

        public AisData.Builder clearCur() {
            this.cur = null;
            this.fieldSetFlags()[9] = false;
            return this;
        }

        public Map<CharSequence, CharSequence> getCus() {
            return this.cus;
        }

        public AisData.Builder setCus(Map<CharSequence, CharSequence> value) {
            this.validate(this.fields()[10], value);
            this.cus = value;
            this.fieldSetFlags()[10] = true;
            return this;
        }

        public boolean hasCus() {
            return this.fieldSetFlags()[10];
        }

        public AisData.Builder clearCus() {
            this.cus = null;
            this.fieldSetFlags()[10] = false;
            return this;
        }

        public AisData build() {
            try {
                AisData e = new AisData();
                e.mid = this.fieldSetFlags()[0]?this.mid:((Long)this.defaultValue(this.fields()[0])).longValue();
                e.db = this.fieldSetFlags()[1]?this.db:(CharSequence)this.defaultValue(this.fields()[1]);
                e.sch = this.fieldSetFlags()[2]?this.sch:(CharSequence)this.defaultValue(this.fields()[2]);
                e.tab = this.fieldSetFlags()[3]?this.tab:(CharSequence)this.defaultValue(this.fields()[3]);
                e.opt = this.fieldSetFlags()[4]?this.opt:(CharSequence)this.defaultValue(this.fields()[4]);
                e.ts = this.fieldSetFlags()[5]?this.ts:((Long)this.defaultValue(this.fields()[5])).longValue();
                e.ddl = this.fieldSetFlags()[6]?this.ddl:(CharSequence)this.defaultValue(this.fields()[6]);
                e.err = this.fieldSetFlags()[7]?this.err:(CharSequence)this.defaultValue(this.fields()[7]);
                e.src = this.fieldSetFlags()[8]?this.src:(Map)this.defaultValue(this.fields()[8]);
                e.cur = this.fieldSetFlags()[9]?this.cur:(Map)this.defaultValue(this.fields()[9]);
                e.cus = this.fieldSetFlags()[10]?this.cus:(Map)this.defaultValue(this.fields()[10]);
                return e;
            } catch (Exception var2) {
                throw new AvroRuntimeException(var2);
            }
        }
    }
}
