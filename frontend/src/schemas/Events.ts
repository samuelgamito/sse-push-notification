export interface EventsSettingsResponse {
  id?: string;
  alias: string;
  routingKey?: string;
  description: string;
  createdAt?: Date;
  updatedAt?: Date;
}

interface Metadata {
  [key: string]: string;
}

export interface EventHistory {
  id: string;
  alias: string;
  message: string;
  metadata: Metadata;
  publishedAt: Date;
  updatedAt: Date;
  createdAt: Date;
}
