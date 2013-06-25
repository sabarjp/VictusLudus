
package com.teamderpy.victusludus.language;

import java.util.Random;

/**
 * Creates psuedo-latin words
 * @author Josh
 * 
 */
public class LatinGenerator implements IWordGenerator {
	protected final static String[] PREFIX_LIST = {"Ab", "Abs", "Ad", "Ac", "Ambi", "Ante", "Ben", "Bon", "Bi", "Bircum", "Con",
		"Com", "Co", "Contra", "Contro", "Counter", "De", "Dis", "Di", "Ex", "Extra", "Extro", "In", "Im", "Ig", "In", "Ip",
		"Infra", "Infer", "Inter", "Intra", "Meg", "Magn", "Mag", "Maj", "Migr", "Mill", "Mis", "Multi", "Non", "Oc", "Of", "Op",
		"Omni", "Ob", "Per", "Post", "Pre", "Pro", "Re", "Red", "Retro", "Quad", "Quatr", "Se", "Sed", "Sub", "Sus", "Sup",
		"Super", "Trans", "Tran", "Tra", "Ultra", "Un", "Uni", "Val", "Vic", "A", "E", "I", "O", "U"};
	protected final static String[] ROOT_LIST = {"ab-", "a-", "abs-", "ac-", "acerb-", "acid-", "acr-", "acu-", "ad-", "a-",
		"ac-", "af-", "ag-", "al-", "ap-", "ar-", "as-", "at-", "adip-", "agri-", "-egri-", "alb-", "am-", "amat-", "ambi-",
		"amic-", "-imic-", "ampl-", "anim-", "ann-", "-enn-", "ante-", "anti-", "aqu-", "ar-", "ar-", "argent-", "audi-", "aug-",
		"auct-", "aur-", "auri-", "avi-", "axi-", "bac-", "be-", "beat-", "bell-", "ben-", "bi-", "bib-", "bon-", "bov-", "brev-",
		"bucc-", "bulb-", "bull-", "burs-", "cad-", "-cid-", "cas-", "caed-", "-cid-", "caes-", "-cis-", "calor-", "camer-",
		"camp-", "can-", "can-", "-cin-", "cant-", "cand-", "cap-", "-cip-", "capt-", "-cept-", "capit-", "-cipit-", "capr-",
		"caps-", "carbo-", "carcer-", "cardin-", "carn-", "cast-", "caten-", "caud-", "caus-", "-cus-", "cav-", "ced-", "cess-",
		"celer-", "cens-", "cent-", "centen-", "centesim-", "cern-", "cervic-", "ceter-", "cili-", "ciner-", "cing-", "cinct-",
		"circ-", "circum-", "cirr-", "civ-", "clar-", "claud-", "-clud-", "claus-", "-clus-", "clement-", "clin-", "cogn-", "col-",
		"coll-", "coll-", "color-", "con-", "co-", "col-", "com-", "cor-", "condi-", "contra-", "cord-", "corn-", "coron-",
		"corpor-", "cortic-", "cost-", "crass-", "cre-", "cred-", "cribr-", "crisp-", "crist-", "cruc-", "crur-", "cub-", "culin-",
		"culp-", "cune-", "cur-", "curr-", "curs-", "curv-", "cuspid-", "cut-", "damn-", "-demn-", "de-", "deb-", "decim-", "den-",
		"dens-", "dent-", "dexter-", "dict-", "digit-", "doc-", "doct-", "dom-", "don-", "dorm-", "dors-", "du-", "dub-", "duc-",
		"duct-", "dulc-", "dur-", "ed-", "es-", "em-", "empt-", "emul-", "ens-", "equ-", "-iqu-", "equ-", "err-", "ex-", "e-",
		"ef-", "exter-", "extra-", "extrem-", "f-", "fat-", "fab-", "fac-", "-fic-", "fact", "-fect-", "falc-", "fall-", "-fell-",
		"fals-", "fallac-", "famili-", "fasc-", "fatu-", "feder-", "fel-", "felic-", "fell-", "femin-", "femor-", "fend-", "fens-",
		"fenestr-", "fer-", "feroc-", "ferr-", "fet-", "fic-", "fid-", "fis-", "fil-", "fili-", "fin-", "find-", "fiss-", "firm-",
		"fistul-", "fl-", "flacc-", "flav-", "flect-", "flex-", "flig-", "flict-", "flor-", "flu-", "flux-", "foc-", "fod-",
		"foss-", "foen-", "foli-", "font-", "for-", "form-", "fornic-", "fort-", "fove-", "frang-", "-fring-", "fract-", "frag-",
		"frater-", "fratr-", "fric-", "frict-", "frig-", "front-", "fruct-", "frug-", "fug-", "fugit-", "fum-", "fund-", "fund-",
		"fus-", "fung-", "funct-", "fur-", "furt-", "furc-", "fusc-", "gel-", "ger-", "gest-", "germin-", "glabr-", "glaci-",
		"gladi-", "glob-", "glori-", "glutin-", "grad-", "-gred-", "gress-", "gran-", "grand-", "grat-", "grav-", "greg-",
		"gubern-", "gust-", "gutt-", "guttur-", "hab-", "-hib-", "habit-", "-hibit-", "hal-", "-hel-", "haur-", "haust-", "her-",
		"hes-", "herb-", "hered-", "hibern-", "hiem-", "hirsut-", "hispid-", "histri-", "homin-", "honor-", "hort-", "hospit-",
		"host-", "hum-", "ign-", "in-", "im-", "in-", "il-", "im-", "ir-", "infra-", "insul-", "inter-", "intra-", "irasc-",
		"irat-", "irid-", "iter-", "itiner-", "jac-", "jac-", "-ject-", "janu-", "joc-", "judic-", "jug-", "jung-", "junct-",
		"junior-", "jus-", "jur-", "juv-", "jut-", "juven-", "juxta-", "lab-", "laps-", "labi-", "labor-", "lacer-", "lacrim-",
		"lact-", "lamin-", "lapid-", "larg-", "larv-", "lat-", "later-", "laud-", "laus-", "lav-", "lax-", "led-", "les-", "leg-",
		"leg-", "leni-", "leon-", "lev-", "liber-", "libr-", "lig-", "limac-", "lin-", "lingu-", "linqu-", "lict-", "liter-",
		"loc-", "long-", "loqu-", "locut-", "luc-", "lud-", "lus-", "lumin-", "lun-", "magn-", "maj-", "mal-", "mamm-", "man-",
		"man-", "mand-", "manu-", "mar-", "mater-", "matr-", "maxim-", "medi-", "-midi-", "melior-", "mell-", "memor-", "menstru-",
		"mensur-", "ment-", "merc-", "merg-", "mers-", "mic-", "migr-", "milit-", "mill-", "millen-", "min-", "min-", "mir-",
		"misce-", "mixt-", "mitt-", "miss-", "mol-", "moll-", "monil-", "mont-", "mord-", "mort-", "mov-", "mot-", "mulg-",
		"muls-", "mult-", "mur-", "mus-", "musc-", "mut-", "nar-", "narr-", "nas-", "nasc-", "nat-", "nav-", "nect-", "nex-",
		"neg-", "nemor-", "nict-", "nigr-", "nihil-", "noct-", "nod-", "nomin-", "non-", "non-", "nonagen-", "nonagesim-", "not-",
		"nov-", "nov-", "noven-", "novendec-", "nox-", "noc-", "nu-", "nub-", "nuc-", "nuch-", "nud-", "null-", "numer-", "nunci-",
		"nupti-", "nutri-", "ob-", "o-", "oc-", "of-", "og-", "op-", "os-", "oct-", "octav-", "octogen-", "octogesim-", "octon-",
		"ocul-", "od-", "odor-", "ole-", "oliv-", "omas-", "oment-", "omin-", "omni-", "oner-", "opac-", "oper-", "opercul-",
		"opt-", "optim-", "or-", "orb-", "ordin-", "ori-", "ort-", "orn-", "oscill-", "oss-", "osti-", "ov-", "ovi-", "pac-",
		"pagin-", "pal-", "pall-", "palli-", "palm-", "palustr-", "pand-", "pans-", "pariet-", "part-", "parv-", "pasc-", "past-",
		"pass-", "passer-", "pat-", "pati-", "pass-", "patr-", "pauc-", "pav-", "pecc-", "pector-", "pecun-", "ped-", "pejor-",
		"pell-", "puls-", "pen-", "pend-", "pens-", "penn-", "pinn-", "per-", "pessim-", "pet-", "pic-", "pil-", "pin-", "ping-",
		"pict-", "pingu-", "pir-", "pisc-", "plac-", "plac-", "-plic-", "plan-", "plang-", "planct-", "plaud-", "-plod-", "plaus-",
		"-plos-", "ple-", "plet-", "pleb-", "plect-", "plex-", "plen-", "plic-", "plor-", "plu-", "plum-", "plumb-", "plur-",
		"plurim-", "plus-", "pluvi-", "pollic-", "pollin-", "pon-", "posit-", "ponder-", "pont-", "popul-", "porc-", "port-",
		"port-", "post-", "pot-", "prat-", "prav-", "pre-", "prec-", "pred-", "prehend-", "prend-", "prehens-", "prem-", "-prim-",
		"press-", "preter-", "preti-", "prim-", "prior-", "priv-", "pro-", "prob-", "propri-", "proxim-", "prun-", "pub-",
		"public-", "pude-", "pugn-", "pulchr-", "pulmon-", "pulver-", "pung-", "punct-", "puni-", "pup-", "pur-", "purg-",
		"purpur-", "put-", "quadr-", "quart-", "quasi-", "quatern-", "quati-", "quass-", "quer-", "-quir-", "quesit-", "-quisit-",
		"qui-", "quin-", "quindecim-", "quinden-", "quinque-", "quint-", "quot-", "rad-", "ras-", "radi-", "radic-", "ram-",
		"ran-", "ranc-", "rap-", "rar-", "rauc-", "re-", "red-", "reg-", "-rig-", "rect-", "rem-", "ren-", "rep-", "rept-", "ret-",
		"retro-", "rid-", "ris-", "robor-", "rod-", "ros-", "rog-", "rostr-", "rot-", "ruber-", "rubr-", "rug-", "rumin-", "rump-",
		"rupt-", "rur-", "sacr-", "secr-", "sagac-", "sagitt-", "sal-", "sali-", "-sili-", "salt-", "salic-", "salv-", "san-",
		"sanc-", "sanguin-", "sapi-", "-sipi-", "sapon-", "sax-", "scab-", "scal-", "scand-", "-scend-", "scans-", "-scens-",
		"sci-", "scind-", "sciss-", "scrib-", "script-", "sculp-", "scut-", "se-", "sed-", "seb-", "sec-", "sect-", "seg-", "sed-",
		"sed-", "-sid-", "sess-", "sedec-", "seget-", "sell-", "semi-", "semin-", "sen-", "sen-", "senti-", "sens-", "sept-",
		"sept-", "septen-", "septim-", "sequ-", "secut-", "ser-", "sat-", "ser-", "ser-", "serp-", "serr-", "serv-", "sesqui-",
		"set-", "sever-", "sex-", "se-", "sexagen-", "sext-", "sibil-", "sicc-", "sider-", "sign-", "sil-", "silv-", "simi-",
		"simil-", "simul-", "singul-", "sinistr-", "sinu-", "sinus-", "sist-", "soci-", "sol-", "sol-", "sol-", "solv-", "solut-",
		"somn-", "somni-", "son-", "sorb-", "sorpt-", "sord-", "soror-", "spati-", "spec-", "-spic-", "spect-", "spect-",
		"specul-", "sper-", "spic-", "spin-", "spir-", "spond-", "spons-", "spu-", "sput-", "squal-", "squam-", "squarros-", "st-",
		"stagn-", "stann-", "statu-", "-stitu-", "stell-", "stern-", "strat-", "still-", "stimul-", "stingu-", "stinct-", "strig-",
		"strigos-", "string-", "strict-", "stru-", "struct-", "stud-", "stup-", "su-", "sut-", "sui-", "suad-", "suas-", "suav-",
		"sub-", "su-", "suf-", "sug-", "sus-", "subter-", "sucr-", "sud-", "sulc-", "sum-", "sumpt-", "super-", "supin-", "supra-",
		"surd-", "surg-", "tac-", "-tic-", "tal-", "tang-", "-ting-", "tact-", "tag-", "tapet-", "tard-", "taur-", "teg-", "tect-",
		"tempor-", "ten-", "-tin-", "tent-", "tend-", "tens-", "tenu-", "tep-", "ter-", "trit-", "teret-", "terg-", "ters-",
		"termin-", "tern-", "terr-", "terti-", "test-", "tex-", "text-", "tim-", "ting-", "tinct-", "torpe-", "torqu-", "tort-",
		"tot-", "trab-", "trah-", "tract-", "trans-", "tra-", "tran-", "trecent-", "tredec-", "trem-", "tri-", "tricen-",
		"tricesim-", "trigesim-", "trin-", "trit-", "trud-", "trus-", "tuss-", "uber-", "uligin-", "ultim-", "ultra-", "umbilic-",
		"umbr-", "un-", "unc-", "unci-", "und-", "undecim-", "unden-", "ungui-", "ungul-", "urb-", "urg-", "urs-", "ut-", "us-",
		"uv-", "uxor-", "vac-", "vad-", "vas-", "vag-", "van-", "vap-", "veh-", "vect-", "vel-", "vell-", "vuls-", "veloc-",
		"ven-", "ven-", "ven-", "vent-", "vend-", "vener-", "vent-", "ventr-", "ver-", "verb-", "verber-", "verm-", "vern-",
		"vert-", "vers-", "vesic-", "vesper-", "vest-", "vestig-", "vet-", "veter-", "vi-", "vic-", "vicen-", "vigen-", "vicesim-",
		"vigesim-", "vid-", "vis-", "vil-", "vill-", "vin-", "vinc-", "vict-", "vir-", "vir-", "visc-", "viscer-", "vit-",
		"vitell-", "viti-", "vitr-", "viv-", "voc-", "vol-", "vol-", "volv-", "volut-", "vom-", "vor-", "vorac-", "vov-", "vot-",
		"vulg-", "vulner-", "vulp-"};
	protected final static String[] SUFFIX_LIST = {"age", "ance", "ant", "ar", "ary", "ence", "ent", "ic", "ine", "ion", "ation",
		"ist", "ive", "ment", "or", "ory", "ty", "ate", "ly", "us", "lum", "reus", "cus", "rus", "tus", "mus"};

	private Random rand;

	public LatinGenerator () {
		this.rand = new Random();
	}

	public LatinGenerator (final long seed) {
		this.rand = new Random(seed);
	}

	@Override
	public String getWord () {
		return LangUtil.capitalize(LangUtil.deduplicate(this.rootize(this.getRoot())));
	}

	@Override
	public String getWord (final long seed) {
		this.rand.setSeed(seed);
		return this.getWord();
	}

	private String getPrefix () {
		return LatinGenerator.PREFIX_LIST[this.rand.nextInt(LatinGenerator.PREFIX_LIST.length)];
	}

	private String getRoot () {
		return LatinGenerator.ROOT_LIST[this.rand.nextInt(LatinGenerator.ROOT_LIST.length)];
	}

	private String getSuffix () {
		return LatinGenerator.SUFFIX_LIST[this.rand.nextInt(LatinGenerator.SUFFIX_LIST.length)];
	}

	private String rootize (final String root) {
		String prefix = "";
		String suffix = "";

		if (root.charAt(0) == '-') {
			prefix = this.getPrefix();
		}

		if (root.charAt(root.length() - 1) == '-') {
			suffix = this.getSuffix();
		}

		return prefix + root.replace("-", "") + suffix;
	}
}
