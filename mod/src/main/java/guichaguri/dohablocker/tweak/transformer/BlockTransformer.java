package guichaguri.dohablocker.tweak.transformer;

import guichaguri.dohablocker.BlockerManager;
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

    private final String loginMethodNameDeobf = "processLoginStart";
    private final String loginMethodNameSrg = "func_147316_a";
    private final String loginMethodNameObf = "a";

    private final String loginMethodDescDeobf = "(Lnet/minecraft/network/login/client/CPacketLoginStart;)V";
    private final String loginMethodDescObf = "(Ljy;)V";

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if(!name.equals(authClassNameObf) && !name.equals(authClassNameDeobf)) return bytes;
        BlockerManager.LOG.info("Patching " + transformedName + "... (" + name + ")");

        ClassNode clazz = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept(clazz, 0);

        boolean patched = false;

        for(MethodNode method : clazz.methods) {
            String mn = method.name;
            if(!mn.equals(loginMethodNameSrg)) {
                if(!mn.equals(loginMethodNameObf) && !mn.equals(loginMethodNameDeobf)) continue;
                String md = method.desc;
                if(!md.equals(loginMethodDescObf) && !md.equals(loginMethodDescDeobf)) continue;
            }

            for(int i = 0; i < method.instructions.size(); i++) {
                AbstractInsnNode node = method.instructions.get(i);
                if(node instanceof LabelNode) {
                    patchMethod(method, (LabelNode)node);
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

    private void patchMethod(MethodNode method, LabelNode firstLabel) {
        String desc = "(";
        desc += method.localVariables.get(0).desc; // NetHandlerLoginServer
        desc += method.localVariables.get(1).desc; // CPacketLoginStart
        desc += ")Z";

        InsnList list = new InsnList();
        list.add(new LabelNode());
        list.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this
        list.add(new VarInsnNode(Opcodes.ALOAD, 1)); // packet
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "guichaguri/dohablocker/vanilla/BlockerHooks", "isBlocked", desc, false));
        list.add(new JumpInsnNode(Opcodes.IFEQ, firstLabel));
        list.add(new InsnNode(Opcodes.RETURN));

        method.instructions.insertBefore(firstLabel, list);
    }
}
