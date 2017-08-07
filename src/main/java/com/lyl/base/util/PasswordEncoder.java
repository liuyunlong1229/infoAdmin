package com.lyl.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * <p>MD5 implementation of PasswordEncoder.</p>
 * <p>If a <code>null</code> password is presented, it will be treated as an empty <code>String</code> ("")
 * password.</p>
 * <P>As MD5 is a one-way hash, the salt can contain any characters.</p>
 *
 * This is a convenience class that extends the
 * {@link MessageDigestPasswordEncoder} and passes MD5 as the algorithm to use.
 *
 * @author Ray Krueger
 * @author colin sampaleanu
 * @author Ben Alex
 */

@Component
public class PasswordEncoder {
	
	private  String algorithm="MD5";
    
    private int iterations = 1;
    
    private boolean encodeHashAsBase64 = false;
    
    
    public PasswordEncoder() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    /**
     * The digest algorithm to use
     * Supports the named <a href="http://java.sun.com/j2se/1.4.2/docs/guide/security/CryptoSpec.html#AppA">
     * Message Digest Algorithms</a> in the Java environment.
     *
     * @param algorithm
     */
    public PasswordEncoder(String algorithm) {
        this(algorithm, false);
    }

    /**
     * Convenience constructor for specifying the algorithm and whether or not to enable base64 encoding
     *
     * @param algorithm
     * @param encodeHashAsBase64
     * @throws IllegalArgumentException if an unknown
     */
    public PasswordEncoder(String algorithm, boolean encodeHashAsBase64) throws IllegalArgumentException {
        this.algorithm = algorithm;
        this.encodeHashAsBase64=encodeHashAsBase64;
        //Validity Check
        getMessageDigest();
    }
    
    
    protected final MessageDigest getMessageDigest() throws IllegalArgumentException {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
        }
    }
    
    

    /**
     * Encodes the rawPass using a MessageDigest.
     * If a salt is specified it will be merged with the password before encoding.
     *
     * @param rawPass The plain text password
     * @param salt The salt to sprinkle
     * @return Hex string of password digest (or base64 encoded string if encodeHashAsBase64 is enabled.
     */
    public String encodePassword(String rawPass, Object salt) {
        String saltedPass = mergePasswordAndSalt(rawPass, salt, false);

        MessageDigest messageDigest = getMessageDigest();

        byte[] digest = messageDigest.digest(Utf8.encode(saltedPass));

        // "stretch" the encoded value if configured to do so
        for (int i = 1; i < iterations; i++) {
            digest = messageDigest.digest(digest);
        }

        if (getEncodeHashAsBase64()) {
            return Utf8.decode(Base64.encode(digest));
        } else {
            return new String(Hex.encode(digest));
        }
    }
    
    public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public boolean getEncodeHashAsBase64() {
		return encodeHashAsBase64;
	}

	public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
		this.encodeHashAsBase64 = encodeHashAsBase64;
	}

	/**
     * Used by subclasses to generate a merged password and salt <code>String</code>.<P>The generated password
     * will be in the form of <code>password{salt}</code>.</p>
     *  <p>A <code>null</code> can be passed to either method, and will be handled correctly. If the
     * <code>salt</code> is <code>null</code> or empty, the resulting generated password will simply be the passed
     * <code>password</code>. The <code>toString</code> method of the <code>salt</code> will be used to represent the
     * salt.</p>
     *
     * @param password the password to be used (can be <code>null</code>)
     * @param salt the salt to be used (can be <code>null</code>)
     * @param strict ensures salt doesn't contain the delimiters
     *
     * @return a merged password and salt <code>String</code>
     *
     * @throws IllegalArgumentException if the salt contains '{' or '}' characters.
     */
    protected String mergePasswordAndSalt(String password, Object salt, boolean strict) {
        if (password == null) {
            password = "";
        }

        if (strict && (salt != null)) {
            if ((salt.toString().lastIndexOf("{") != -1) || (salt.toString().lastIndexOf("}") != -1)) {
                throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
            }
        }

        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }
    

    /**
     * Takes a previously encoded password and compares it with a rawpassword after mixing in the salt and
     * encoding that value
     *
     * @param encPass previously encoded password
     * @param rawPass plain text password
     * @param salt salt to mix into password
     * @return true or false
     */
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        String pass1 = "" + encPass;
        String pass2 = encodePassword(rawPass, salt);

        return PasswordEncoderUtils.equals(pass1,pass2);
    }
     
}

class PasswordEncoderUtils {

    /**
     * Constant time comparison to prevent against timing attacks.
     * @param expected
     * @param actual
     * @return
     */
    static boolean equals(String expected, String actual) {
        byte[] expectedBytes = bytesUtf8(expected);
        byte[] actualBytes = bytesUtf8(actual);
        int expectedLength = expectedBytes == null ? -1 : expectedBytes.length;
        int actualLength = actualBytes == null ? -1 : actualBytes.length;
        if (expectedLength != actualLength) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < expectedLength; i++) {
            result |= expectedBytes[i] ^ actualBytes[i];
        }
        return result == 0;
    }

    private static byte[] bytesUtf8(String s) {
        if(s == null) {
            return null;
        }

        return Utf8.encode(s);
    }
    private PasswordEncoderUtils() {}
}
