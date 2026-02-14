# LLD Documentation

Each LLD problem has its own folder under `docs/` with:

- **design.md** — Design doc (requirements, entities, class diagram reference, extensibility).
- **diagrams/** — PlantUML source and generated SVG:
  - **class-diagram.puml** — PlantUML source (edit this).
  - **class-diagram.svg** — Generated from `.puml`; referenced in `design.md`.

## Generating SVGs (PlantUML)

PlantUML is not required locally. SVGs are generated in CI:

- **GitHub Actions:** [.github/workflows/plantuml.yml](../../.github/workflows/plantuml.yml) at the **repository root** runs on push (when `.puml` files change) and commits the generated SVGs. It discovers all `**/docs/*/diagrams/*.puml` and generates the corresponding `.svg` files.

## Adding a new LLD problem

1. Create `docs/<problem-name>/design.md`.
2. Create `docs/<problem-name>/diagrams/` with `class-diagram.puml`.
3. In `design.md`, include: `![Class Diagram](diagrams/class-diagram.svg)`.
4. Push; the workflow will generate `class-diagram.svg` (or run the workflow manually).
