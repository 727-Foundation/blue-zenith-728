#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;
varying vec2 oneTexel;

uniform vec2 InSize;

uniform vec2 BlurDir;
uniform float Radius;

void main() {
    vec4 blurred = vec4(0.0);
    float totalStrength = 0.0;
    float totalAlpha = 0.0;
    float totalSamples = 0.0;
    for(float r = -Radius; r <= Radius; r += 1.0) {
        vec4 sample = texture2D(DiffuseSampler, texCoord + oneTexel * r * BlurDir);

		// Accumulate average alpha
        totalAlpha = totalAlpha + sample.a;
        /*totalSamples = totalSamples + 1.0;

		// Accumulate smoothed blur
        float strength = 1.0 - abs(r / Radius);
        totalStrength = totalStrength + strength;
        blurred = blurred + sample;*/
    }
    //gl_FragColor = vec4(blurred.rgb / (Radius * 2.0 + 1.0), totalAlpha);

    vec2 newtc = texCoord.xy;
    int nSteps = 20;
    int center = 10;		//=nSteps-1 / 2

    vec3 blur = vec3(0.0);
    float tw = 0.0;

    for (int i = 0; i < nSteps; i++) {
        float dist = abs(i-float(center))/center;
        float weight = (exp(-(dist*dist)/ 0.28));

        vec2 funny = BlurDir * (i-center);
        vec3 bsample = texture2D(DiffuseSampler, (newtc + 2.0*oneTexel*funny)).rgb;
        if (bsample == vec3(0.0, 0.0, 0.0)) {
            continue;
        }

        blur += bsample*weight;
        tw += weight;
    }

    blur = blur / tw;
    blur = clamp(blur,0.0,1.0); //fix flashing black square

    gl_FragColor = vec4(blur, totalAlpha);
    //gl_FragData[0] = vec4(blur, 1.0);
}
