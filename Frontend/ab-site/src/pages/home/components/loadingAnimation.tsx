import { Image } from "react-bootstrap";

interface IProps {
  size: number;
}

export const LoadingAnimation = ({ size }: IProps) => {
  const LOADING_GIF = "/assets/loading.gif";

  return <Image src={LOADING_GIF} width={size} height={size} fluid />;
};
