varying vec4 color;

const float WORLD_RADIUS         = 50.0;
const float WORLD_RADIUS_SQUARED = 2500.0;


void main() {
	vec4 position = gl_ModelViewMatrix * gl_Vertex;
		
	float distanceSquared = position.x * position.x + position.z * position.z;
	
	position.y -= WORLD_RADIUS - sqrt(max(1.0 - distanceSquared / WORLD_RADIUS_SQUARED, 0.0)) * WORLD_RADIUS;
	
	gl_Position = gl_ProjectionMatrix * position;
	
	color = gl_Color;
}