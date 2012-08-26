varying vec4 color;

varying vec3 N;
varying vec3 v;

const float WORLD_RADIUS         = 50.0;
const float WORLD_RADIUS_SQUARED = 2500.0;


void main() {
	v = vec3(gl_ModelViewMatrix * gl_Vertex);
	//N = normalize(gl_NormalMatrix * gl_Normal);
	N = vec3(0, 1, 1);

	vec4 position = gl_ModelViewMatrix * gl_Vertex;
	float distanceSquared = position.x * position.x + position.z * position.z;
	position.y -= WORLD_RADIUS - sqrt(max(1.0 - distanceSquared / WORLD_RADIUS_SQUARED, 0.0)) * WORLD_RADIUS;
	gl_Position = gl_ProjectionMatrix * position;
	color = gl_Color;
}