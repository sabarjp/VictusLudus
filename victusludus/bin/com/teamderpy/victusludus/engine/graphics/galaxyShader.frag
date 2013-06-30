#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 vColor;
varying vec2 vTexCoord;

uniform sampler2D u_texture;

const float blurSize = 1.0/512.0;
const float intensity = 0.25;

void main() {
	vec4 sum = vec4(0);
	vec4 texColor = texture2D(u_texture, vTexCoord);
	
    // blur in y (vertical)
    // take nine samples, with the distance blurSize between them
    sum += texture2D(u_texture, vec2(vTexCoord.x - 4.0*blurSize, vTexCoord.y)) * 0.05;
    sum += texture2D(u_texture, vec2(vTexCoord.x - 3.0*blurSize, vTexCoord.y)) * 0.09;
    sum += texture2D(u_texture, vec2(vTexCoord.x - 2.0*blurSize, vTexCoord.y)) * 0.12;
    sum += texture2D(u_texture, vec2(vTexCoord.x - blurSize, vTexCoord.y)) * 0.15;
    sum += texture2D(u_texture, vec2(vTexCoord.x, vTexCoord.y)) * 0.16;
    sum += texture2D(u_texture, vec2(vTexCoord.x + blurSize, vTexCoord.y)) * 0.15;
    sum += texture2D(u_texture, vec2(vTexCoord.x + 2.0*blurSize, vTexCoord.y)) * 0.12;
    sum += texture2D(u_texture, vec2(vTexCoord.x + 3.0*blurSize, vTexCoord.y)) * 0.09;
    sum += texture2D(u_texture, vec2(vTexCoord.x + 4.0*blurSize, vTexCoord.y)) * 0.05;
	
	// blur in y (vertical)
    // take nine samples, with the distance blurSize between them
    sum += texture2D(u_texture, vec2(vTexCoord.x, vTexCoord.y - 4.0*blurSize)) * 0.05;
    sum += texture2D(u_texture, vec2(vTexCoord.x, vTexCoord.y - 3.0*blurSize)) * 0.09;
    sum += texture2D(u_texture, vec2(vTexCoord.x, vTexCoord.y - 2.0*blurSize)) * 0.12;
    sum += texture2D(u_texture, vec2(vTexCoord.x, vTexCoord.y - blurSize)) * 0.15;
    sum += texture2D(u_texture, vec2(vTexCoord.x, vTexCoord.y)) * 0.16;
    sum += texture2D(u_texture, vec2(vTexCoord.x, vTexCoord.y + blurSize)) * 0.15;
    sum += texture2D(u_texture, vec2(vTexCoord.x, vTexCoord.y + 2.0*blurSize)) * 0.12;
    sum += texture2D(u_texture, vec2(vTexCoord.x, vTexCoord.y + 3.0*blurSize)) * 0.09;
    sum += texture2D(u_texture, vec2(vTexCoord.x, vTexCoord.y + 4.0*blurSize)) * 0.05;

	gl_FragColor = texColor * sum;
}