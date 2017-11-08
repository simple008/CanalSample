package com.bupt.liu.util;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.log4j.Logger;
import org.apache.orc.TypeDescription;

import java.io.UnsupportedEncodingException;

/**
 * Created by lpeiz on 2017/11/6.
 * Hive 中数据类型的处理
 */
public enum HiveDataType {
    TINYINT {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createByte();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            LongColumnVector lv = (LongColumnVector)vector;
            if(value == null) {
                lv.vector[row] = 1L;
                lv.isNull[row] = true;
                lv.noNulls = false;
            } else {
                lv.vector[row] = Long.valueOf(value).longValue();
            }

        }
    },
    SMALLINT {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createShort();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            LongColumnVector lv = (LongColumnVector)vector;
            if(value == null) {
                lv.vector[row] = 1L;
                lv.isNull[row] = true;
                lv.noNulls = false;
            } else {
                lv.vector[row] = Long.valueOf(value).longValue();
            }

        }
    },
    INT {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createInt();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            LongColumnVector lv = (LongColumnVector)vector;
            if(value == null) {
                lv.vector[row] = 1L;
                lv.isNull[row] = true;
                lv.noNulls = false;
            } else {
                lv.vector[row] = Long.valueOf(value).longValue();
            }

        }
    },
    BIGINT {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createLong();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            LongColumnVector lv = (LongColumnVector)vector;
            if(value == null) {
                lv.vector[row] = 1L;
                lv.isNull[row] = true;
                lv.noNulls = false;
            } else {
                lv.vector[row] = Long.valueOf(value).longValue();
            }

        }
    },
    BOOLEAN {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createBoolean();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            LongColumnVector lv = (LongColumnVector)vector;
            if(value == null) {
                lv.vector[row] = 1L;
                lv.isNull[row] = true;
                lv.noNulls = false;
            } else {
                boolean b = Boolean.valueOf(value).booleanValue();
                lv.vector[row] = b?1L:0L;
            }

        }
    },
    FLOAT {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createFloat();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            DoubleColumnVector dv = (DoubleColumnVector)vector;
            if(value == null) {
                dv.vector[row] = 0.0D / 0.0;
                dv.isNull[row] = true;
                dv.noNulls = false;
            } else {
                dv.vector[row] = Double.parseDouble(value);
            }

        }
    },
    DOUBLE {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createDouble();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            DoubleColumnVector dv = (DoubleColumnVector)vector;
            if(value == null) {
                dv.vector[row] = 0.0D / 0.0;
                dv.isNull[row] = true;
                dv.noNulls = false;
            } else {
                dv.vector[row] = Double.parseDouble(value);
            }

        }
    },
    STRING {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createString();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            BytesColumnVector bv = (BytesColumnVector)vector;
            int i = bv.length.length;

            try {
                if(value == null) {
                    bv.vector[row] = null;
                    bv.isNull[row] = true;
                    bv.noNulls = false;
                } else {
                    HiveDataType.LOG.debug("Vector null [" + (vector == null) + "], value null [" + (value == null) + "] , row [" + row + "]");
                    bv.setVal(row, value.getBytes("UTF-8"));
                }
            } catch (UnsupportedEncodingException var7) {
                HiveDataType.LOG.error("When use UTF-8 charset to getbytes occur Exception : " + var7.getMessage(), var7);
            }

        }
    },
    BINARY {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createBinary();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            BytesColumnVector bv = (BytesColumnVector)vector;

            try {
                if(value == null) {
                    bv.vector[row] = null;
                    bv.isNull[row] = true;
                    bv.noNulls = false;
                } else {
                    bv.setVal(row, value.getBytes("UTF-8"));
                }
            } catch (UnsupportedEncodingException var6) {
                HiveDataType.LOG.error("When use UTF-8 charset to getbytes occur Exception : " + var6.getMessage(), var6);
            }

        }
    },
    TIMESTAMP {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createTimestamp();
        }

        public void setValue(ColumnVector vector, int row, String value) {
        }
    },
    DECIMAL {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createDecimal();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            DecimalColumnVector dv = (DecimalColumnVector)vector;
            if(value == null) {
                dv.setNullDataValue(row);
            } else {
                dv.set(row, HiveDecimal.create(value));
            }

        }
    },
    CHAR {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createChar();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            BytesColumnVector bv = (BytesColumnVector)vector;
            bv.setVal(row, value.getBytes());
        }
    },
    VARCHAR {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createVarchar();
        }

        public void setValue(ColumnVector vector, int row, String value) {
            BytesColumnVector bv = (BytesColumnVector)vector;

            try {
                if(value == null) {
                    bv.vector[row] = null;
                    bv.isNull[row] = true;
                    bv.noNulls = false;
                } else {
                    bv.setVal(row, value.getBytes("UTF-8"));
                }
            } catch (UnsupportedEncodingException var6) {
                HiveDataType.LOG.error("When use UTF-8 charset to getbytes occur Exception : " + var6.getMessage(), var6);
            }

        }
    },
    DATE {
        public TypeDescription toOrcTypeDescption() {
            return TypeDescription.createDate();
        }

        public void setValue(ColumnVector vector, int row, String value) {
        }
    };

    private static final Logger LOG;

    private HiveDataType() {
    }

    public abstract TypeDescription toOrcTypeDescption();

    public abstract void setValue(ColumnVector var1, int var2, String var3);

    public static void setValue(ColumnVector vector, int row, String type, String value) {
        HiveDataType hdtype = valueOf(removeOtherChars(type.toUpperCase()));
        LOG.debug("setValue row [" + row + "], type [" + type + "], value [" + value + "]");
        hdtype.setValue(vector, row, value);
    }

    private static String removeOtherChars(String type) {
        return StringUtils.substringBefore(type, "(");
    }

    public static HiveDataType findType(String type) {
        return valueOf(removeOtherChars(type.toUpperCase()));
    }

    static {
        LOG = Logger.getLogger(HiveDataType.class);
    }
}

