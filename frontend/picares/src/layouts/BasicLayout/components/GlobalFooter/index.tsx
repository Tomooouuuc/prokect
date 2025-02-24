import "./index.css";

export default function GlobalFooter() {
  const year = new Date().getFullYear();

  return (
    <div className="global-footer">
      <div>@ {year} picares</div>
    </div>
  );
}
