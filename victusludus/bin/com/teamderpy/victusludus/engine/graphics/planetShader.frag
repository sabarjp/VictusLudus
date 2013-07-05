#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 vColor;
varying LOWP vec4 vOverlayUV;
varying LOWP vec4 vRegionSize;
varying vec2 vTexCoord;

uniform sampler2D u_texture;
uniform sampler2D u_texture1;
uniform sampler2D u_mask;

void main() {	
	vec2 vRegionUV1 = vec2(vOverlayUV.s, vOverlayUV.t);
	vec2 vRegionUV2 = vec2(vOverlayUV.p, vOverlayUV.q);

    //sample the color from the first texture
    vec4 texColor0 = texture2D(u_texture, vTexCoord);
    
    //calculate correct texture coordinates
    vec2 texMod1 = min(vRegionUV2,
                       vec2(vRegionUV1.s + ((vRegionUV2.s - vRegionUV1.s) * (vRegionSize.z/vRegionSize.x)),
    					    vRegionUV1.t + ((vRegionUV2.t - vRegionUV1.t) * (vRegionSize.w/vRegionSize.y))));
    
    //sample the color from the second texture
    vec4 texColor1 = texture2D(u_texture1, mix(vRegionUV1, vRegionUV2, vTexCoord));
    
    //use mask
    float mask = texColor0.a;
    
    //interpolate color based on mask
    gl_FragColor = vColor * mix(texColor0, texColor1, mask);
}