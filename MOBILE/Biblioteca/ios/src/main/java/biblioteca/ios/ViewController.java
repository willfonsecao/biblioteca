package biblioteca.ios;


import Http.HttpClientFactory;
import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSBundle;
import apple.foundation.NSCoder;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import apple.uikit.UIStoryboardSegue;
import apple.uikit.UIViewController;
import org.moe.natj.c.ann.FunctionPtr;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.NInt;
import org.moe.natj.general.ann.NUInt;
import org.moe.natj.general.ann.Owned;
import org.moe.natj.general.ann.RegisterOnStartup;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.VoidPtr;
import org.moe.natj.objc.Class;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.SEL;
import org.moe.natj.objc.ann.ObjCClassBinding;
import org.moe.natj.objc.ann.ObjCClassName;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

import java.io.IOException;

import apple.uikit.UILabel;
import apple.uikit.UIButton;
import model.Editora;
import service.EditoraService;

@Runtime(ObjCRuntime.class)
@ObjCClassName("ViewController")
@RegisterOnStartup
public class ViewController extends UIViewController {
	static {
		NatJ.register();
	}

	String toSegue2 = "toSegue2";
    String textoVolta = "";

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();
            titulo().setText(textoVolta);
    }

    @Override
    public void prepareForSegueSender(UIStoryboardSegue segue, @Mapped(ObjCObjectMapper.class) Object sender) {
        super.prepareForSegueSender(segue, sender);

        if(this.toSegue2.equals(segue.identifier())){
           ViewSegue2Controller view2 = (ViewSegue2Controller) segue.destinationViewController();
            view2.nomeOrigem = "Eu vim pela Segue 1";

        }
    }

    @Selector("sendToSegue:")
    public void sendToSegue(UIButton sender){
        EditoraService editoraService = HttpClientFactory.getHttpClient().create(EditoraService.class);
        Editora editora = new Editora();
        editora.setNome("Editora Nova");
        try {
            editoraService.salvarEditora(editora).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        performSegueWithIdentifierSender(toSegue2,this);
    }

    @Generated
	protected ViewController(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native ViewController alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("attemptRotationToDeviceOrientation")
	public static native void attemptRotationToDeviceOrientation();

	@Generated
	@Selector("automaticallyNotifiesObserversForKey:")
	public static native boolean automaticallyNotifiesObserversForKey(String key);

	@Generated
	@Selector("cancelPreviousPerformRequestsWithTarget:")
	public static native void cancelPreviousPerformRequestsWithTarget(
			@Mapped(ObjCObjectMapper.class) Object aTarget);

	@Generated
	@Selector("cancelPreviousPerformRequestsWithTarget:selector:object:")
	public static native void cancelPreviousPerformRequestsWithTargetSelectorObject(
			@Mapped(ObjCObjectMapper.class) Object aTarget, SEL aSelector,
			@Mapped(ObjCObjectMapper.class) Object anArgument);

	@Generated
	@Selector("classFallbacksForKeyedArchiver")
	public static native NSArray<String> classFallbacksForKeyedArchiver();

	@Generated
	@Selector("classForKeyedUnarchiver")
	public static native Class classForKeyedUnarchiver();

	@Generated
	@Selector("clearTextInputContextIdentifier:")
	public static native void clearTextInputContextIdentifier(String identifier);

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native ViewController init();

	@Generated
	@Selector("initWithCoder:")
	public native ViewController initWithCoder(NSCoder aDecoder);

	@Generated
	@Selector("initWithNibName:bundle:")
	public native ViewController initWithNibNameBundle(String nibNameOrNil,
			NSBundle nibBundleOrNil);

	@Generated
	@Selector("instanceMethodForSelector:")
	@FunctionPtr(name = "call_instanceMethodForSelector_ret")
	public static native NSObject.Function_instanceMethodForSelector_ret instanceMethodForSelector(
			SEL aSelector);

	@Generated
	@Selector("instanceMethodSignatureForSelector:")
	public static native NSMethodSignature instanceMethodSignatureForSelector(
			SEL aSelector);

	@Generated
	@Selector("instancesRespondToSelector:")
	public static native boolean instancesRespondToSelector(SEL aSelector);

	@Generated
	@Selector("isSubclassOfClass:")
	public static native boolean isSubclassOfClass(Class aClass);

	@Generated
	@Selector("keyPathsForValuesAffectingValueForKey:")
	public static native NSSet<String> keyPathsForValuesAffectingValueForKey(
			String key);

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();

	@Generated
	@Selector("setTitulo:")
	public native void setTitulo_unsafe(UILabel value);

	@Generated
	public void setTitulo(UILabel value) {
		Object __old = titulo();
		if (value != null) {
			org.moe.natj.objc.ObjCRuntime.associateObjCObject(this, value);
		}
		setTitulo_unsafe(value);
		if (__old != null) {
			org.moe.natj.objc.ObjCRuntime.dissociateObjCObject(this, __old);
		}
	}

	@Generated
	@Selector("titulo")
	public native UILabel titulo();

}