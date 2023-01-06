package egovframework.com.api.crypto.LEA.symm;

import egovframework.com.api.crypto.LEA.BlockCipher;
import egovframework.com.api.crypto.LEA.engine.LeaEngine;
import egovframework.com.api.crypto.LEA.mac.CMac;
import egovframework.com.api.crypto.LEA.mode.CBCMode;
import egovframework.com.api.crypto.LEA.mode.CCMMode;
import egovframework.com.api.crypto.LEA.mode.CFBMode;
import egovframework.com.api.crypto.LEA.mode.CTRMode;
import egovframework.com.api.crypto.LEA.mode.ECBMode;
import egovframework.com.api.crypto.LEA.mode.GCMMode;
import egovframework.com.api.crypto.LEA.mode.OFBMode;

public class LEA {
	private LEA() {
		throw new AssertionError();
	}

	public static final BlockCipher getEngine() {
		return new LeaEngine();
	}

	public static final class ECB extends ECBMode {
		public ECB() {
			super(getEngine());
		}
	}

	public static final class CBC extends CBCMode {
		public CBC() {
			super(getEngine());
		}
	}

	public static final class CTR extends CTRMode {
		public CTR() {
			super(getEngine());
		}
	}

	public static final class CFB extends CFBMode {
		public CFB() {
			super(getEngine());
		}
	}

	public static final class OFB extends OFBMode {
		public OFB() {
			super(getEngine());
		}
	}

	public static final class CCM extends CCMMode {
		public CCM() {
			super(getEngine());
		}
	}

	public static final class GCM extends GCMMode {
		public GCM() {
			super(getEngine());
		}
	}

	public static final class CMAC extends CMac {
		public CMAC() {
			super(getEngine());
		}
	}

}
