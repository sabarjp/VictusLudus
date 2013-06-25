
package com.teamderpy.victusludus.language;

import java.util.Random;

/**
 * Creates pseudo-greek words
 * @author Josh
 * 
 */
public class GreekGenerator implements IWordGenerator {
	protected final static String[] PREFIX_LIST = {"Anti", "Ant", "Auto", "Bio", "Bi", "Hyper", "Mono", "Neo", "Pan", "Therm",
		"Eu", "Caco", "Mega", "Tele", "Tel", "Archae", "Homo", "Olig", "Poly", "Iso", "Hemi", "Tauto", "Paleo", "Neo", "Hier",
		"Idio", "Holo", "Allo", "Tri", "Ennea", "Di", "Dys", "Acro", "A", "E", "I", "O", "U"};
	protected final static String[] ROOT_LIST = {"acr-", "aer-", "aesthet-", "agr-", "amph-", "amphi-", "an-", "a-", "ana-",
		"an-", "andr-", "anem-", "ant-", "anti-", "anth-", "anthra-", "anthrop-", "ap-", "apo-", "arche-", "archi-", "archae-",
		"arche-", "arct-", "arist-", "arthr-", "astr-", "athl-", "-athroid", "aut-", "auto-", "axi-", "bar-", "basi-", "bathy-",
		"batho-", "bibl-", "bio-", "blenn-", "blast-", "botan-", "brachi-", "brachy-", "brady-", "branchi-", "brom-", "brom-",
		"brom-", "bronch-", "bront-", "cac-", "call-", "calli-", "calyp-", "cardi-", "carp-", "carp-", "cata-", "cat-", "cathar-",
		"cen-", "cen-", "centr-", "cephal-", "ceram-", "cerat-", "chir-", "chelon-", "chlor-", "chore-", "chrom-", "chron-",
		"chrys-", "cine-", "cirr-", "clad-", "clast-", "clav-", "clist-", "cleithr-", "cochl-", "coel-", "con-", "copr-", "corac-",
		"cosm-", "cosmet-", "cotyl-", "-cracy", "-crat", "crani-", "crep-", "cric-", "cris-", "crit-", "cross-", "crypt-", "cten-",
		"cub-", "cyan-", "cycl-", "cylind-", "cyn-", "cyst-", "cyt-", "dactyl-", "deca-", "dec-", "deka-", "dek-", "delt-", "dem-",
		"dendr-", "derm-", "deuter-", "dexi-", "di-", "dia-", "dino-", "dipl-", "dodec-", "dogmat-", "dox-", "dy-", "dyna-",
		"dys-", "ec-", "eccles-", "eco-", "ecto-", "ego-", "eg-", "elect-", "eme-", "en-", "em-", "encephalo-", "endo-", "engy-",
		"ennea-", "eo-", "eos-", "eoso-", "ep-", "epi-", "epistem-", "erg-", "erythr-", "eso-", "etho-", "eth-", "ethi-", "ethm-",
		"ethn-", "etym-", "eu-", "eur-", "exo-", "fant-", "galact-", "gastr-", "geo-", "gen-", "glia-", "gramm-", "graph-",
		"gymn-", "gyn-", "hadr-", "haem-", "hem-", "hal-", "hapl-", "hedo-", "heli-", "hemi-", "hen-", "hendec-", "hept-", "herp-",
		"heter-", "heur-", "hex-", "hier-", "hipp-", "hod-", "hol-", "hom-", "homal-", "home-", "hor-", "hor-", "horm-", "hyal-",
		"hydr-", "hygr-", "hyo-", "hyp-", "hyper-", "hypn-", "hyster-", "ichthy-", "icos-", "id-", "ide-", "idi-", "is-", "iso-",
		"kil-", "kine-", "klept-", "kudo-", "lamp-", "lecith-", "lei-", "lekan-", "lep-", "leps-", "leuc-", "leuk-", "lip-",
		"lith-", "log-", "lysis", "macro-", "mania", "meg-", "mei-", "melan-", "mening-", "men-", "mer-", "mes-", "meter-",
		"metr-", "meta-", "micr-", "mim-", "mis-", "mit-", "mne-", "mon-", "morph-", "my-", "myri-", "myrmec-", "myth-", "myx-",
		"myz-", "narc-", "naut-", "ne-", "necr-", "nect-", "nema-", "nephr-", "nes-", "neur-", "nom-", "nomad-", "noth-", "noto-",
		"oct-", "od-", "odont-", "oeco-", "oed-", "oen-", "oesophag-", "ogdo-", "-oid", "olig-", "-oma", "omm-", "omo-", "omphal-",
		"onom-", "ont-", "-onym", "oo-", "ophi-", "ophthalm-", "opisth-", "opoter-", "opt-", "orch-", "organ-", "ornith-", "orth-",
		"osteo-", "ostrac-", "ot-", "oxy-", "pach-", "paed-", "palae-", "pale-", "palin-", "pan-", "pam-", "par-", "parthen-",
		"path-", "patr-", "pect-", "ped-", "pent-", "pentecost-", "pept-", "peran-", "peri-", "persic-", "petr-", "phae-", "phag-",
		"phalang-", "phalar-", "pharmac-", "phaner-", "pher-", "phil-", "-phile", "phleg-", "phloe-", "phob-", "phon-", "phor-",
		"phos-", "phot-", "phragm-", "phren-", "phryn-", "phyl-", "phyll-", "phys-", "physalid-", "phyt-", "pin-", "pis-", "plac-",
		"plagi-", "plas-", "platy-", "plec-", "plesi-", "pleth-", "pleur-", "plinth-", "plut-", "pneu-", "pod-", "pogon-", "poie-",
		"pol-", "pole-", "poli-", "polem-", "poli-", "poly-", "por-", "porphyr-", "potam-", "prasin-", "presby-", "pro-", "proct-",
		"pros-", "prot-", "psamm-", "pseud-", "psil-", "psych-", "psychr-", "pter-", "pto-", "ptyal-", "ptych-", "pyg-", "pyl-",
		"pyr-", "raph-", "rhabd-", "rhach-", "rach-", "rhag-", "rhe-", "rhig-", "rhin-", "rhiz-", "rhod-", "rhomb-", "rhynch-",
		"sacchar-", "sarc-", "saur-", "scalen-", "scaph-", "scel-", "schem-", "schis-", "scler-", "scoli-", "scop-", "scept-",
		"scyph-", "sei-", "selen-", "sema-", "siph-", "sit-", "solen-", "soma-", "soph-", "sperm-", "sphen-", "spher-", "sphinct-",
		"spondyl-", "stalact-", "stalagm-", "stear-", "steg-", "sten-", "stere-", "stern-", "stich-", "stigmat-", "stoch-",
		"stom-", "strept-", "stroph-", "styl-", "syn-", "sy-", "syl-", "sym-", "syring-", "tach-", "taeni-", "tars-", "taur-",
		"tax-", "techn-", "tele-", "tele-", "temn-", "tetr-", "thalam-", "thalass-", "than-", "the-", "thus-", "the-", "thel-",
		"theori-", "ther-", "therm-", "thym-", "thyr-", "thyre-", "tom-", "ton-", "top-", "tox-", "trachy-", "trag-", "trapez-",
		"traum-", "trema-", "tri-", "trich-", "trit-", "troch-", "trop-", "troph-", "tympan-", "typ-", "ulo-", "ur-", "ur-",
		"xanth-", "xen-", "xer-", "xiph-", "xyl-", "zo-", "zon-", "zyg-", "zym-"};
	protected final static String[] SUFFIX_LIST = {"ist", "ize", "log", "oid", "algia", "lite", "fie", "pnea", "roid", "eat",
		"ian", "ach"};

	private Random rand;

	public GreekGenerator () {
		this.rand = new Random();
	}

	public GreekGenerator (final long seed) {
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
		return GreekGenerator.PREFIX_LIST[this.rand.nextInt(GreekGenerator.PREFIX_LIST.length)];
	}

	private String getRoot () {
		return GreekGenerator.ROOT_LIST[this.rand.nextInt(GreekGenerator.ROOT_LIST.length)];
	}

	private String getSuffix () {
		return GreekGenerator.SUFFIX_LIST[this.rand.nextInt(GreekGenerator.SUFFIX_LIST.length)];
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
