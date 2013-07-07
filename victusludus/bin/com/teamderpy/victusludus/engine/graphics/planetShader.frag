#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 vColor;
varying LOWP vec4 vOverlayUV;
varying LOWP vec4 vTextureUV;
varying LOWP vec4 vRegionSize;
varying LOWP vec4 vUVOffset;
varying vec2 vTexCoord;

uniform sampler2D u_texture;
uniform sampler2D u_texture1;
uniform sampler2D u_mask;

float linearInterpolation (float x1, float x2, float y1, float y2, float desiredX) {
	return y1 + ((y2 - y1) * ((desiredX - x1) / (x2 - x1)));
}

void main() {	
	//the UV coordinates of the overlay texture on the texture sheet
	LOWP vec2 vRegionUV1 = vec2(vOverlayUV[0], vOverlayUV[1]);
	LOWP vec2 vRegionUV2 = vec2(vOverlayUV[2], vOverlayUV[3]);
	
	//the UV coordinates of the actual texture, normalized between 0 and 1 since the original coords
	//map to a texture sheet
	LOWP vec2 vTextureUVNorm = vec2(linearInterpolation(vTextureUV[0], vTextureUV[2], 0, 1, vTexCoord[0]), linearInterpolation(vTextureUV[1], vTextureUV[3], 0, 1, vTexCoord[1]));
	
	//normalize our offset to 0...2
	float uvOffset = vUVOffset[0] / 180.0;
	
	//add the offset
	float tempUV = vTextureUVNorm.s + uvOffset;
	
	
	//wrap back the texture if necessary
	if(tempUV > 2.0){
		tempUV = fract(tempUV);
	}else if(tempUV > 1.0){
		tempUV = 1.0 + (1.0 - tempUV);
	} else {
		tempUV;
	}
	
	vTextureUVNorm.s = tempUV;

    //sample the color from the first texture
    vec4 texColor0 = texture2D(u_texture, vTexCoord);
    
    //calculate correct texture coordinates
    vec2 texMod1 = min(vRegionUV2,
                       vec2(vRegionUV1.s + ((vRegionUV2.s - vRegionUV1.s) * (vRegionSize.z/vRegionSize.x)),
    					    vRegionUV1.t + ((vRegionUV2.t - vRegionUV1.t) * (vRegionSize.w/vRegionSize.y))));
    
    //sample the color from the second texture
    vec4 texColor1 = texture2D(u_texture1, mix(vRegionUV1, texMod1, vTextureUVNorm));
    
    //use mask
    float mask = texColor0.a;
    
    //interpolate color based on mask
    gl_FragColor = vColor * mix(texColor0, texColor1, mask);
    //gl_FragColor = vColor * texColor1;
}