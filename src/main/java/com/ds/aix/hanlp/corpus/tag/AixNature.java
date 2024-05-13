package com.ds.aix.hanlp.corpus.tag;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ds
 * @date 2024/5/11
 * @description
 */
public class AixNature {

    /*---------------------- 自定义类型  ------------------------*/
    // 公司名
    public static final AixNature hzc = new AixNature("hzc");
    // 问题
    public static final AixNature hzq = new AixNature("hzq");

    /*---------------------- 原有类型  --------------------------*/
    public static final AixNature bg = new AixNature("bg");
    public static final AixNature mg = new AixNature("mg");
    public static final AixNature nl = new AixNature("nl");
    public static final AixNature nx = new AixNature("nx");
    public static final AixNature qg = new AixNature("qg");
    public static final AixNature ud = new AixNature("ud");
    public static final AixNature uj = new AixNature("uj");
    public static final AixNature uz = new AixNature("uz");
    public static final AixNature ug = new AixNature("ug");
    public static final AixNature ul = new AixNature("ul");
    public static final AixNature uv = new AixNature("uv");
    public static final AixNature yg = new AixNature("yg");
    public static final AixNature zg = new AixNature("zg");
    public static final AixNature n = new AixNature("n");
    public static final AixNature nr = new AixNature("nr");
    public static final AixNature nrj = new AixNature("nrj");
    public static final AixNature nrf = new AixNature("nrf");
    public static final AixNature nr1 = new AixNature("nr1");
    public static final AixNature nr2 = new AixNature("nr2");
    public static final AixNature ns = new AixNature("ns");
    public static final AixNature nsf = new AixNature("nsf");
    public static final AixNature nt = new AixNature("nt");
    public static final AixNature ntc = new AixNature("ntc");
    public static final AixNature ntcf = new AixNature("ntcf");
    public static final AixNature ntcb = new AixNature("ntcb");
    public static final AixNature ntch = new AixNature("ntch");
    public static final AixNature nto = new AixNature("nto");
    public static final AixNature ntu = new AixNature("ntu");
    public static final AixNature nts = new AixNature("nts");
    public static final AixNature nth = new AixNature("nth");
    public static final AixNature nh = new AixNature("nh");
    public static final AixNature nhm = new AixNature("nhm");
    public static final AixNature nhd = new AixNature("nhd");
    public static final AixNature nn = new AixNature("nn");
    public static final AixNature nnt = new AixNature("nnt");
    public static final AixNature nnd = new AixNature("nnd");
    public static final AixNature ng = new AixNature("ng");
    public static final AixNature nf = new AixNature("nf");
    public static final AixNature ni = new AixNature("ni");
    public static final AixNature nit = new AixNature("nit");
    public static final AixNature nic = new AixNature("nic");
    public static final AixNature nis = new AixNature("nis");
    public static final AixNature nm = new AixNature("nm");
    public static final AixNature nmc = new AixNature("nmc");
    public static final AixNature nb = new AixNature("nb");
    public static final AixNature nba = new AixNature("nba");
    public static final AixNature nbc = new AixNature("nbc");
    public static final AixNature nbp = new AixNature("nbp");
    public static final AixNature nz = new AixNature("nz");
    public static final AixNature g = new AixNature("g");
    public static final AixNature gm = new AixNature("gm");
    public static final AixNature gp = new AixNature("gp");
    public static final AixNature gc = new AixNature("gc");
    public static final AixNature gb = new AixNature("gb");
    public static final AixNature gbc = new AixNature("gbc");
    public static final AixNature gg = new AixNature("gg");
    public static final AixNature gi = new AixNature("gi");
    public static final AixNature j = new AixNature("j");
    public static final AixNature i = new AixNature("i");
    public static final AixNature l = new AixNature("l");
    public static final AixNature t = new AixNature("t");
    public static final AixNature tg = new AixNature("tg");
    public static final AixNature s = new AixNature("s");
    public static final AixNature f = new AixNature("f");
    public static final AixNature v = new AixNature("v");
    public static final AixNature vd = new AixNature("vd");
    public static final AixNature vn = new AixNature("vn");
    public static final AixNature vshi = new AixNature("vshi");
    public static final AixNature vyou = new AixNature("vyou");
    public static final AixNature vf = new AixNature("vf");
    public static final AixNature vx = new AixNature("vx");
    public static final AixNature vi = new AixNature("vi");
    public static final AixNature vl = new AixNature("vl");
    public static final AixNature vg = new AixNature("vg");
    public static final AixNature a = new AixNature("a");
    public static final AixNature ad = new AixNature("ad");
    public static final AixNature an = new AixNature("an");
    public static final AixNature ag = new AixNature("ag");
    public static final AixNature al = new AixNature("al");
    public static final AixNature b = new AixNature("b");
    public static final AixNature bl = new AixNature("bl");
    public static final AixNature z = new AixNature("z");
    public static final AixNature r = new AixNature("r");
    public static final AixNature rr = new AixNature("rr");
    public static final AixNature rz = new AixNature("rz");
    public static final AixNature rzt = new AixNature("rzt");
    public static final AixNature rzs = new AixNature("rzs");
    public static final AixNature rzv = new AixNature("rzv");
    public static final AixNature ry = new AixNature("ry");
    public static final AixNature ryt = new AixNature("ryt");
    public static final AixNature rys = new AixNature("rys");
    public static final AixNature ryv = new AixNature("ryv");
    public static final AixNature rg = new AixNature("rg");
    public static final AixNature Rg = new AixNature("Rg");
    public static final AixNature m = new AixNature("m");
    public static final AixNature mq = new AixNature("mq");
    public static final AixNature Mg = new AixNature("Mg");
    public static final AixNature q = new AixNature("q");
    public static final AixNature qv = new AixNature("qv");
    public static final AixNature qt = new AixNature("qt");
    public static final AixNature d = new AixNature("d");
    public static final AixNature dg = new AixNature("dg");
    public static final AixNature dl = new AixNature("dl");
    public static final AixNature p = new AixNature("p");
    public static final AixNature pba = new AixNature("pba");
    public static final AixNature pbei = new AixNature("pbei");
    public static final AixNature c = new AixNature("c");
    public static final AixNature cc = new AixNature("cc");
    public static final AixNature u = new AixNature("u");
    public static final AixNature uzhe = new AixNature("uzhe");
    public static final AixNature ule = new AixNature("ule");
    public static final AixNature uguo = new AixNature("uguo");
    public static final AixNature ude1 = new AixNature("ude1");
    public static final AixNature ude2 = new AixNature("ude2");
    public static final AixNature ude3 = new AixNature("ude3");
    public static final AixNature usuo = new AixNature("usuo");
    public static final AixNature udeng = new AixNature("udeng");
    public static final AixNature uyy = new AixNature("uyy");
    public static final AixNature udh = new AixNature("udh");
    public static final AixNature uls = new AixNature("uls");
    public static final AixNature uzhi = new AixNature("uzhi");
    public static final AixNature ulian = new AixNature("ulian");
    public static final AixNature e = new AixNature("e");
    public static final AixNature y = new AixNature("y");
    public static final AixNature o = new AixNature("o");
    public static final AixNature h = new AixNature("h");
    public static final AixNature k = new AixNature("k");
    public static final AixNature x = new AixNature("x");
    public static final AixNature xx = new AixNature("xx");
    public static final AixNature xu = new AixNature("xu");
    public static final AixNature w = new AixNature("w");
    public static final AixNature wkz = new AixNature("wkz");
    public static final AixNature wky = new AixNature("wky");
    public static final AixNature wyz = new AixNature("wyz");
    public static final AixNature wyy = new AixNature("wyy");
    public static final AixNature wj = new AixNature("wj");
    public static final AixNature ww = new AixNature("ww");
    public static final AixNature wt = new AixNature("wt");
    public static final AixNature wd = new AixNature("wd");
    public static final AixNature wf = new AixNature("wf");
    public static final AixNature wn = new AixNature("wn");
    public static final AixNature wm = new AixNature("wm");
    public static final AixNature ws = new AixNature("ws");
    public static final AixNature wp = new AixNature("wp");
    public static final AixNature wb = new AixNature("wb");
    public static final AixNature wh = new AixNature("wh");
    public static final AixNature end = new AixNature("end");
    public static final AixNature begin = new AixNature("begin");
    private static ConcurrentHashMap<String, Integer> idMap;
    private static AixNature[] values;
    private final int ordinal;
    private final String name;

    private AixNature(String name) {
        if (idMap == null) {
            idMap = new ConcurrentHashMap<>();
        }

        assert !idMap.containsKey(name);

        this.name = name;
        this.ordinal = idMap.size();
        idMap.put(name, this.ordinal);
        AixNature[] extended = new AixNature[idMap.size()];
        if (values != null) {
            System.arraycopy(values, 0, extended, 0, values.length);
        }

        extended[this.ordinal] = this;
        values = extended;
    }

    public boolean startsWith(String prefix) {
        return this.name.startsWith(prefix);
    }

    public boolean startsWith(char prefix) {
        return this.name.charAt(0) == prefix;
    }

    public char firstChar() {
        return this.name.charAt(0);
    }

    public static AixNature fromString(String name) {
        Integer id = (Integer)idMap.get(name);
        return id == null ? null : values[id];
    }

    public static AixNature create(String name) {
        AixNature AixNature = fromString(name);
        return AixNature == null ? new AixNature(name) : AixNature;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int ordinal() {
        return this.ordinal;
    }

    public static AixNature[] values() {
        return values;
    }

}
