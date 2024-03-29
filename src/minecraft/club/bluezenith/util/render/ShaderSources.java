package club.bluezenith.util.render;

public class ShaderSources {
    public static String mainMenuBackground2 = "#ifdef GL_ES\n" +
            "precision highp float;\n" +
            "#endif\n" +
            "\n" +
            "uniform float time;\n" +
            "uniform vec2 mouse;\n" +
            "uniform vec2 resolution;\n" +
            "#define iGlobalTime time\n" +
            "#define iTime time\n" +
            "#define iMouse (mouse * resolution)\n" +
            "#define iResolution resolution.xy\n" +
            "\n" +
            "mat2 mm2(in float a){float c = cos(a), s = sin(a);return mat2(c,s,-s,c);}\n" +
            "mat2 m2 = mat2(0.95534, 0.29552, -0.29552, 0.95534);\n" +
            "float tri(in float x){return clamp(abs(fract(x)-.5),0.01,0.49);}\n" +
            "vec2 tri2(in vec2 p){return vec2(tri(p.x)+tri(p.y),tri(p.y+tri(p.x)));}\n" +
            "\n" +
            "float triNoise2d(in vec2 p, float spd)\n" +
            "{\n" +
            "    float z=1.8;\n" +
            "    float z2=2.5;\n" +
            "\tfloat rz = 0.;\n" +
            "    p *= mm2(p.x*0.06);\n" +
            "    vec2 bp = p;\n" +
            "\tfor (float i=0.; i<5.; i++ )\n" +
            "\t{\n" +
            "        vec2 dg = tri2(bp*1.85)*.75;\n" +
            "        dg *= mm2(time*spd);\n" +
            "        p -= dg/z2;\n" +
            "\n" +
            "        bp *= 1.3;\n" +
            "        z2 *= .45;\n" +
            "        z *= .42;\n" +
            "\t\tp *= 1.21 + (rz-1.0)*.02;\n" +
            "        \n" +
            "        rz += tri(p.x+tri(p.y))*z;\n" +
            "        p*= -m2;\n" +
            "\t}\n" +
            "    return clamp(1./pow(rz*29., 1.3),0.,.55);\n" +
            "}\n" +
            "\n" +
            "float hash21(in vec2 n){ return fract(sin(dot(n, vec2(12.9898, 4.1414))) * 43758.5453); }\n" +
            "vec4 aurora(vec3 ro, vec3 rd)\n" +
            "{\n" +
            "    vec4 col = vec4(0);\n" +
            "    vec4 avgCol = vec4(0);\n" +
            "    \n" +
            "    for(float i=0.;i<50.;i++)\n" +
            "    {\n" +
            "        float of = 0.006*hash21(gl_FragCoord.xy)*smoothstep(0.,15., i);\n" +
            "        float pt = ((.8+pow(i,1.4)*.002)-ro.y)/(rd.y*2.+0.4);\n" +
            "        pt -= of;\n" +
            "    \tvec3 bpos = ro + pt*rd;\n" +
            "        vec2 p = bpos.zx;\n" +
            "        float rzt = triNoise2d(p, 0.06);\n" +
            "        vec4 col2 = vec4(0,0,0, rzt);\n" +
            "        col2.rgb = (sin(1.-vec3(2.15,-.5, 1.2)+i*0.043)*0.5+0.5)*rzt;\n" +
            "        avgCol =  mix(avgCol, col2, .5);\n" +
            "        col += avgCol*exp2(-i*0.065 - 2.5)*smoothstep(0.,5., i);\n" +
            "        \n" +
            "    }\n" +
            "    \n" +
            "    col *= (clamp(rd.y*15.+.4,0.,1.));\n" +
            "    \n" +
            "    \n" +
            "    //return clamp(pow(col,vec4(1.3))*1.5,0.,1.);\n" +
            "    //return clamp(pow(col,vec4(1.7))*2.,0.,1.);\n" +
            "    //return clamp(pow(col,vec4(1.5))*2.5,0.,1.);\n" +
            "    //return clamp(pow(col,vec4(1.8))*1.5,0.,1.);\n" +
            "    \n" +
            "    //return smoothstep(0.,1.1,pow(col,vec4(1.))*1.5);\n" +
            "    return col*1.8;\n" +
            "    //return pow(col,vec4(1.))*2.\n" +
            "}\n" +
            "\n" +
            "\n" +
            "//-------------------Background and Stars--------------------\n" +
            "\n" +
            "//From Dave_Hoskins (https://www.shadertoy.com/view/4djSRW)\n" +
            "vec3 hash33(vec3 p)\n" +
            "{\n" +
            "    p = fract(p * vec3(443.8975,397.2973, 491.1871));\n" +
            "    p += dot(p.zxy, p.yxz+19.27);\n" +
            "    return fract(vec3(p.x * p.y, p.z*p.x, p.y*p.z));\n" +
            "}\n" +
            "\n" +
            "vec3 stars(in vec3 p)\n" +
            "{\n" +
            "    vec3 c = vec3(0.);\n" +
            "    float res = iResolution.x*1.;\n" +
            "    \n" +
            "\tfor (float i=0.;i<4.;i++)\n" +
            "    {\n" +
            "        vec3 q = fract(p*(.15*res))-0.5;\n" +
            "        vec3 id = floor(p*(.15*res));\n" +
            "        vec2 rn = hash33(id).xy;\n" +
            "        float c2 = 1.-smoothstep(0.,.6,length(q));\n" +
            "        c2 *= step(rn.x,.0005+i*i*0.001);\n" +
            "        c += c2*(mix(vec3(1.0,0.49,0.1),vec3(0.75,0.9,1.),rn.y)*0.1+0.9);\n" +
            "        p *= 1.3;\n" +
            "    }\n" +
            "    return c*c*.8;\n" +
            "}\n" +
            "\n" +
            "vec3 bg(in vec3 rd)\n" +
            "{\n" +
            "    float sd = dot(normalize(vec3(-0.5, -0.6, 0.9)), rd)*0.5+0.5;\n" +
            "    sd = pow(sd, 5.);\n" +
            "    vec3 col = mix(vec3(0.05,0.1,0.2), vec3(0.1,0.05,0.2), sd);\n" +
            "    return col*.63;\n" +
            "}\n" +
            "//-----------------------------------------------------------\n" +
            "\n" +
            "\n" +
            "void mainImage( out vec4 fragColor, in vec2 fragCoord )\n" +
            "{\n" +
            "\tvec2 q = fragCoord.xy / iResolution.xy;\n" +
            "    vec2 p = q - 0.5;\n" +
            "\tp.x*=iResolution.x/iResolution.y;\n" +
            "    \n" +
            "    vec3 ro = vec3(0,0,-6.7);\n" +
            "    vec3 rd = normalize(vec3(p,1.3));\n" +
            "    vec2 mo = iMouse.xy / iResolution.xy-.5;\n" +
            "    mo = (mo==vec2(-.5))?mo=vec2(-0.1,0.1):mo;\n" +
            "\tmo.x *= iResolution.x/iResolution.y;\n" +
            "    rd.yz *= mm2(mo.y);\n" +
            "    rd.xz *= mm2(mo.x + sin(time*0.05)*0.2);\n" +
            "    \n" +
            "    vec3 col = vec3(0.);\n" +
            "    vec3 brd = rd;\n" +
            "    float fade = smoothstep(0.,0.01,abs(brd.y))*0.1+0.9;\n" +
            "    \n" +
            "    col = bg(rd)*fade;\n" +
            "    \n" +
            "    if (rd.y > 0.){\n" +
            "        vec4 aur = smoothstep(0.,1.5,aurora(ro,rd))*fade;\n" +
            "        //col += stars(rd);\n" +
            "        col = col*(1.-aur.a) + aur.rgb;\n" +
            "    }\n" +
            "    /*else //Reflections\n" +
            "    {\n" +
            "        rd.y = abs(rd.y);\n" +
            "        col = bg(rd)*fade*0.6;\n" +
            "        vec4 aur = smoothstep(0.0,2.5,aurora(ro,rd));\n" +
            "        col += stars(rd)*0.1;\n" +
            "        col = col*(1.-aur.a) + aur.rgb;\n" +
            "        vec3 pos = ro + ((0.5-ro.y)/rd.y)*rd;\n" +
            "        float nz2 = triNoise2d(pos.xz*vec2(.5,.7), 0.);\n" +
            "        col += mix(vec3(0.2,0.25,0.5)*0.08,vec3(0.3,0.3,0.5)*0.7, nz2*0.4);\n" +
            "    }*/\n" +
            "    \n" +
            "\tfragColor = vec4(col, 1.);\n" +
            "}\n" +
            "void main( void ) {\n" +
            "\tvec4 colo = vec4(0.0);\n" +
            "\tmainImage(colo, gl_FragCoord.xy);\n" +
            "\tgl_FragColor = colo;\n" +
            "}";

    public static String mainMenuBackground = "#extension GL_OES_standard_derivatives : enable\n" +
            "\n" +
            "#ifdef GL_ES\n" +
            "precision highp float;\n" +
            "#endif\n" +
            "\n" +
            "#define OCTAVES 8\n" +
            "\n" +
            "uniform vec2 resolution;\n" +
            "uniform float time;\n" +
            "\n" +
            "float random(in vec2 st)\n" +
            "{\n" +
            "    return fract(sin(dot(st.xy, vec2(12.9898, 78.233))) * 43758.5453123);\n" +
            "}\n" +
            "\n" +
            "float noise(in vec2 st)\n" +
            "{\n" +
            "    vec2 i = floor(st);\n" +
            "    vec2 f = fract(st);\n" +
            "\n" +
            "    float a = random(i);\n" +
            "    float b = random(i + vec2(1.0, 0.0));\n" +
            "    float c = random(i + vec2(0.0, 1.0));\n" +
            "    float d = random(i + vec2(1.0, 1.0));\n" +
            "\n" +
            "    vec2 u = f * f * (3.0 - 2.0 * f);\n" +
            "\n" +
            "    return mix(a, b, u.x) + (c - a)* u.y * (1.0 - u.x) + (d - b) * u.x * u.y;\n" +
            "}\n" +
            "\n" +
            "float fbm(in vec2 st)\n" +
            "{\n" +
            "    float val = 0.0;\n" +
            "    float amp = 0.5;\n" +
            "\n" +
            "    for (int i = 0; i < OCTAVES; i++)\n" +
            "    {\n" +
            "        val += amp * noise(st);\n" +
            "        st *= 2.0;\n" +
            "        amp *= 0.5;\n" +
            "    }\n" +
            "    return val;\n" +
            "}\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    vec2 st = (gl_FragCoord.xy / resolution.xy) + (time * 0.01);\n" +
            "    st.x *= (resolution.x / resolution.y) + time * 0.0001;\n" +
            "\n" +
            "    vec3 color = vec3(0.0431372549, 0.47058823529, 0.98823529411);\n" +
            "    color *= fbm(st * 3.0);\n" +
            "\n" +
            "    gl_FragColor = vec4(color, 1.0);\n" +
            "}";

    public static String vertexShader = "#version 120\n" +
            "\n" +
            "void main(){\n" +
            "    gl_Position = gl_Vertex;\n" +
            "}";
}
