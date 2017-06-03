package guichaguri.boha.tweak.transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * @author Guilherme Chaguri
 */
public class BlockTransformer implements IClassTransformer {

    private final String authClassNameDeobf = "net.minecraft.server.network.NetHandlerLoginServer";
    private final String authClassNameObf = "mk";

    private final String gameProfileDesc = "Lcom/mojang/authlib/GameProfile;";

    private final String acceptMethodNameDeobf = "tryAcceptPlayer";
    private final String acceptMethodNameSrg = "func_147326_c";
    private final String acceptMethodNameObf = "b";
    private final String acceptMethodDesc = "()V";

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if(!name.equals(authClassNameObf) && !name.equals(authClassNameDeobf)) return bytes;

        ClassNode clazz = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept(clazz, 0);

        boolean patched = false;

        for(MethodNode method : clazz.methods) {
            String mn = method.name;
            if(!mn.equals(acceptMethodNameSrg)) {
                if(!mn.equals(acceptMethodNameObf) && !mn.equals(acceptMethodNameDeobf)) continue;
                if(!method.desc.equals(acceptMethodDesc)) continue;
            }

            for(int i = 0; i < method.instructions.size(); i++) {
                AbstractInsnNode node = method.instructions.get(i);
                if(node instanceof LabelNode) {
                    patchMethod(clazz, method, (LabelNode)node);
                    patched = true;
                    break;
                }
            }
        }

        if(!patched) return bytes;

        ClassWriter writer = new ClassWriter(0);
        clazz.accept(writer);
        return writer.toByteArray();
    }

    private void patchMethod(ClassNode clazz, MethodNode method, LabelNode firstLabel) {
        FieldNode gameProfile = null;
        for(FieldNode field : clazz.fields) {
            if(field.desc.equals(gameProfileDesc)) {
                gameProfile = field;
                break;
            }
        }

        if(gameProfile == null) return;

        String desc = "(";
        desc += "L" + clazz.name + ";"; // NetHandlerLoginServer
        desc += gameProfile.desc; // GameProfile
        desc += ")Z";

        InsnList list = new InsnList();
        list.add(new LabelNode());
        list.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this
        list.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this
        list.add(new FieldInsnNode(Opcodes.GETFIELD, clazz.name, gameProfile.name, gameProfile.desc));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "guichaguri/boha/vanilla/BlockerHooks", "isBlocked", desc, false));
        list.add(new JumpInsnNode(Opcodes.IFEQ, firstLabel));
        list.add(new InsnNode(Opcodes.RETURN));

        method.instructions.insertBefore(firstLabel, list);
    }
}
